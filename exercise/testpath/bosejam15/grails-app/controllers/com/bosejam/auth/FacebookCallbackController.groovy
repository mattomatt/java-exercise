package com.bosejam.auth

import grails.plugin.facebooksdk.FacebookContext
import grails.plugin.facebooksdk.FacebookGraphClient

class FacebookCallbackController {

    def springSecurityService

    FacebookContext facebookContextProxy

    def index() {

        def user=springSecurityService.currentUser
        def fu=FacebookUser.findByUser(user)
        def facebookClient = new FacebookGraphClient(fu.accessToken)
        def utente = facebookClient.fetchObject("me")
        def mioLink=createLink(absolute: true, controller: "FacebookCallbackController")
        log.debug( mioLink)
        def publishMessageResponse = facebookClient.publish("me/feed", [message:"RestFB test",link:"${mioLink}"])
        log.debug( "Published message ID: " + publishMessageResponse.id)

    }

    def callback() {

    }
}
