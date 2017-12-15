/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

package fr.paris.lutece.plugins.recastbots.business;

import fr.paris.lutece.test.LuteceTestCase;

public class RecastBotBusinessTest extends LuteceTestCase
{
    private final static String BOTKEY1 = "BotKey1";
    private final static String BOTKEY2 = "BotKey2";
    private final static String NAME1 = "Name1";
    private final static String NAME2 = "Name2";
    private final static String DESCRIPTION1 = "Description1";
    private final static String DESCRIPTION2 = "Description2";
    private final static String AVATARURL1 = "AvatarUrl1";
    private final static String AVATARURL2 = "AvatarUrl2";
    private final static String LANGUAGE1 = "Language1";
    private final static String LANGUAGE2 = "Language2";
    private final static int BOTSTATUS1 = 1;
    private final static int BOTSTATUS2 = 2;
    private final static String TOKEN1 = "Token1";
    private final static String TOKEN2 = "Token2";

    public void testBusiness( )
    {
        // Initialize an object
        RecastBot recastBot = new RecastBot( );
        recastBot.setBotKey( BOTKEY1 );
        recastBot.setName( NAME1 );
        recastBot.setDescription( DESCRIPTION1 );
        recastBot.setAvatarUrl( AVATARURL1 );
        recastBot.setLanguage( LANGUAGE1 );
        recastBot.setBotStatus( BOTSTATUS1 );
        recastBot.setToken( TOKEN1 );

        // Create test
        RecastBotHome.create( recastBot );
        RecastBot recastBotStored = RecastBotHome.findByPrimaryKey( recastBot.getId( ) );
        assertEquals( recastBotStored.getBotKey( ), recastBot.getBotKey( ) );
        assertEquals( recastBotStored.getName( ), recastBot.getName( ) );
        assertEquals( recastBotStored.getDescription( ), recastBot.getDescription( ) );
        assertEquals( recastBotStored.getAvatarUrl( ), recastBot.getAvatarUrl( ) );
        assertEquals( recastBotStored.getLanguage( ), recastBot.getLanguage( ) );
        assertEquals( recastBotStored.getBotStatus( ), recastBot.getBotStatus( ) );
        assertEquals( recastBotStored.getToken( ), recastBot.getToken( ) );

        // Update test
        recastBot.setBotKey( BOTKEY2 );
        recastBot.setName( NAME2 );
        recastBot.setDescription( DESCRIPTION2 );
        recastBot.setAvatarUrl( AVATARURL2 );
        recastBot.setLanguage( LANGUAGE2 );
        recastBot.setBotStatus( BOTSTATUS2 );
        recastBot.setToken( TOKEN2 );
        RecastBotHome.update( recastBot );
        recastBotStored = RecastBotHome.findByPrimaryKey( recastBot.getId( ) );
        assertEquals( recastBotStored.getBotKey( ), recastBot.getBotKey( ) );
        assertEquals( recastBotStored.getName( ), recastBot.getName( ) );
        assertEquals( recastBotStored.getDescription( ), recastBot.getDescription( ) );
        assertEquals( recastBotStored.getAvatarUrl( ), recastBot.getAvatarUrl( ) );
        assertEquals( recastBotStored.getLanguage( ), recastBot.getLanguage( ) );
        assertEquals( recastBotStored.getBotStatus( ), recastBot.getBotStatus( ) );
        assertEquals( recastBotStored.getToken( ), recastBot.getToken( ) );

        // List test
        RecastBotHome.getRecastBotsList( );

        // Delete test
        RecastBotHome.remove( recastBot.getId( ) );
        recastBotStored = RecastBotHome.findByPrimaryKey( recastBot.getId( ) );
        assertNull( recastBotStored );

    }

}
