package com.discobeard.steam.api.wrapper.request

import com.discobeard.steam.api.wrapper.BaseSpec
import com.discobeard.steam.api.wrapper.exception.SteamException
import com.discobeard.steam.api.wrapper.request.player.GetPlayerSummariesRequest
import com.fasterxml.jackson.core.JsonParseException

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder

class RequestSpec extends BaseSpec {

    Client client = ClientBuilder.newClient()

    def 'when steam does not respond or entity does not parse then a SteamException is thrown' (){
        given:
            GetPlayerSummariesRequest testObj = new GetPlayerSummariesRequest(client,"http://localhost:10000","key")
        when:
            testObj.submit()
        then:
            SteamException ex = thrown()
            ex.message == "Could not connect response the steam api"
            ex.cause instanceof ConnectException

    }

    def 'when an entity does not parse then a SteamException is thrown' (){
        given:
            GetPlayerSummariesRequest testObj = new GetPlayerSummariesRequest(client,"http://localhost:1080","key")
            returnUnparsablePlayerSummaries()
        when:
            testObj.submit()
        then:
            testObj
            SteamException ex = thrown()
            ex.message == "Could not parse response from steam"
            ex.cause instanceof JsonParseException
    }


}