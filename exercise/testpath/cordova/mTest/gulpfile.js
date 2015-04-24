// generated on 2014-11-11 using generator-m 0.2.7
/* jshint -W079 */ // prevent redefinition of $ warning

'use strict';
var gulp = require('gulp');
var $ = require('gulp-load-plugins')();

var minimist = require('minimist');
var fs = require('fs');
var xml2js = require('xml2js');

// options
var options = minimist(process.argv.slice(2));
options.distPath = 'www';
if (options.cordova) {
  // gulp build before running cordova?
  var cmds = ['build', 'run', 'emulate', 'prepare'];
  for (var i = 0, cmd; (cmd = cmds[i]); i++) {
    if (options.cordova.indexOf(cmd) >= 0) {
      options.runBuild = true;
      break;
    }
  }
}

gulp.task('styles', function () {
  return gulp.src('app/styles/main.scss')
    .pipe($.plumber())
    .pipe($.rubySass({
      style: 'expanded',
      precision: 10
    }))
    .pipe($.autoprefixer('last 1 version'))
    .pipe(gulp.dest('.tmp/styles'));
});

// check for jshint errors
gulp.task('jshint', function () {
  return gulp.src('app/scripts/**/*.js')
    .pipe($.jshint())
    .pipe($.jshint.reporter('jshint-stylish'))
    .pipe($.jshint.reporter('fail'));
});

// check for jscs errors
gulp.task('jscs', function () {
  return gulp.src('app/scripts/**/*.js')
    .pipe($.jscs());
});

// copy partials
gulp.task('partials', function () {
  return gulp.src([
    'app/partials/**/*', // html, language, locales, assets
  ])
  .pipe(gulp.dest(options.distPath + '/partials'));
});

// build starting from main html file (index.html)
gulp.task('app', ['inject', 'styles', 'partials'], function () {
  // only build assets that are actually used
  var assets = $.useref.assets({searchPath: '{.tmp,app}'});

  return gulp.src('app/*.html') // main html file
    // useref - parses build block in html, concatenate & replace files
    .pipe(assets)
    // TODO: implement flag to turn on
    // .pipe($.if('*.js', // angular DI & uglification
    //   $.ngAnnotate({
    //     remove: true,
    //     add: true,
    //     'single_quotes': true
    //   })
    //   .pipe($.uglify())
    // ))
    // .pipe($.if('*.css', $.csso())) // minify css
    .pipe(assets.restore())
    .pipe($.useref())
    .pipe(gulp.dest(options.distPath));
});

// copy & minify images to dist/images
gulp.task('images', function () {
  return gulp.src('app/images/**/*')
    .pipe($.cache($.imagemin({
      progressive: true,
      interlaced: true
    })))
    .pipe(gulp.dest(options.distPath + '/images'));
});

// copy fonts to do dist/fonts and app/fonts
gulp.task('fonts', function () {
  return gulp.src(require('main-bower-files')({filter: /\.(eot|svg|ttf|woff)/i})
    .concat('app/fonts/**/*'))
    .pipe($.flatten())
    .pipe(gulp.dest(options.distPath + '/fonts'))
    .pipe(gulp.dest('app/fonts')); // TODO: find a better way to inject $ionicons-font-path: "../fonts" !default; into main.scss on build
});

gulp.task('clean', function () {
  require('del').sync(['.tmp', options.distPath + '/*']);
});

gulp.task('connect', function () {
  var serveStatic = require('serve-static');
  var serveIndex = require('serve-index');
  var app = require('connect')()
    .use(require('connect-livereload')({port: 35729}))
    .use(serveStatic('app'))
    .use(serveStatic('.tmp'))
    // paths to bower_components should be relative to the current file
    // e.g. in app/index.html you should use ../bower_components
    .use('/bower_components', serveStatic('bower_components'))
    .use(serveIndex('app'));

  require('http').createServer(app)
    .listen(9000)
    .on('listening', function () {
      console.log('Started connect web server on http://localhost:9000');
    });
});

gulp.task('serve', ['connect', 'inject', 'styles'], function () {
  require('opn')('http://localhost:9000');
});

// TODO: do with commandline option - https://github.com/gulpjs/gulp/blob/master/docs/recipes/pass-arguments-from-cli.md
gulp.task('connect-build', function () {
  var serveStatic = require('serve-static');
  var serveIndex = require('serve-index');
  var app = require('connect')()
    .use(require('connect-livereload')({port: 35729}))
    .use(serveStatic(options.distPath))
    // paths to bower_components should be relative to the current file
    // e.g. in app/index.html you should use ../bower_components
    .use('/bower_components', serveStatic('bower_components'))
    .use(serveIndex(options.distPath));

  require('http').createServer(app)
    .listen(9000)
    .on('listening', function () {
      console.log('Started connect web server on http://localhost:9000');
    });
});

gulp.task('serve-build', ['connect-build'], function () {
  require('opn')('http://localhost:9000');
});

// inject bower components
gulp.task('wiredep', function () {
  var wiredep = require('wiredep').stream;
// TODO:
//
//   gulp.src('app/styles/*.scss') // into main.scss
//     .pipe(wiredep())
//     .pipe(gulp.dest('app/styles'));
//
  return gulp.src('app/*.html') // into index.html
    .pipe(wiredep())
    .pipe(gulp.dest('app'));
});

// inject app/**/.*js and cordova.js files into index.html
gulp.task('inject', function () {
  var jsFiles = gulp.src(['./app/scripts/**/*.js']);

  return gulp.src('./app/index.html')
    .pipe($.inject(
      jsFiles
        .pipe($.plumber()) // use plumber so watch doesn't crash on js error
        .pipe($.angularFilesort()),
      {relative: true}))
    .pipe(gulp.dest('./app'));
});

gulp.task('watch', ['connect', 'serve'], function () {
  $.livereload.listen();

  // watch for changes
  gulp.watch([
    'app/*.html',
    '.tmp/styles/**/*.css',
    'app/scripts/**/*.js',
    'app/images/**/*',
    'app/partials/**/*'
  ]).on('change', function () {
    $.livereload.changed();
    gulp.start('inject'); // TODO: only run when added/deleted files
    // FIXME: when deleting second watch is not started: index.html OK but 404 in livereload
    // FIXME: not watching new files?!
  });

  // watch for changes in css/scss
  gulp.watch('app/styles/**/*.scss', ['styles']);
  // watch for changes in bower.json
  gulp.watch('bower.json', ['wiredep']);
});

gulp.task('build', ['clean', 'jshint', 'jscs', 'app', 'images', 'fonts'], function () {
  return gulp.src(options.distPath + '/**/*').pipe($.size({title: 'build', gzip: true}));
});

// CORDOVA
// TODO: find better solution for cordova CLI integration
gulp.task('default', function () {
  if (options.runBuild) {
    return gulp.start('cordova-with-build');
  }
  else {
    return gulp.start('cordova');
  }
});

gulp.task('cordova', function () {
  return gulp.src('')
    .pipe($.shell([
      'node_modules/cordova/bin/cordova ' + options.cordova
    ]));
});

gulp.task('cordova-with-build', ['build'], function () {
  return gulp.src('')
    .pipe($.shell([
      'node_modules/cordova/bin/cordova ' + options.cordova
    ]));
});

// CONFIG for project
gulp.task('config', function () {
  var parser = new xml2js.Parser();
  var builder = new xml2js.Builder({
    renderOp: {
      pretty: true,
      indent: ' '
    },
    xmldec: {
      version: '1.0',
      encoding: 'utf-8'
    }
  });
  var xmlFile = fs.readFileSync(__dirname + '/config.xml');
  parser.parseString(xmlFile, function (err, result) {
    // actual processing
    if (options.setVersion) {
      result.widget.$.version = options.setVersion;
    }
    if (options.setBuild) {
      result.widget.$.version = result.widget.$.version + '.' + options.setBuild;
    }
    if (options.setBundle) {
      result.widget.$.id = options.setBundle;
    }

    var xml = builder.buildObject(result);
    fs.writeFileSync(__dirname + '/config.xml', xml);
  });
});
