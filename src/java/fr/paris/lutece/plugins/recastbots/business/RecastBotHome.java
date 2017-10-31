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

import fr.paris.lutece.plugins.chatbot.service.BotService;
import fr.paris.lutece.plugins.recastbots.service.BotInstance;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for RecastBot objects
 */
public final class RecastBotHome
{
    // Static variable pointed at the DAO instance
    private static IRecastBotDAO _dao = SpringContextService.getBean( "recastbots.recastBotDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "recastbots" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private RecastBotHome(  )
    {
    }

    /**
     * Create an instance of the recastBot class
     * @param recastBot The instance of the RecastBot which contains the informations to store
     * @return The  instance of recastBot which has been created with its primary key.
     */
    public static RecastBot create( RecastBot recastBot )
    {
        _dao.insert( recastBot, _plugin );
        
        BotService.register( new BotInstance( recastBot ));

        return recastBot;
    }

    /**
     * Update of the recastBot which is specified in parameter
     * @param recastBot The instance of the RecastBot which contains the data to store
     * @return The instance of the  recastBot which has been updated
     */
    public static RecastBot update( RecastBot recastBot )
    {
        _dao.store( recastBot, _plugin );
        BotService.unregister( recastBot.getBotKey() );
        BotService.register( new BotInstance( recastBot ));

        return recastBot;
    }

    /**
     * Remove the recastBot whose identifier is specified in parameter
     * @param nKey The recastBot Id
     */
    public static void remove( int nKey )
    {
        RecastBot bot = findByPrimaryKey( nKey );
        BotService.unregister( bot.getBotKey() );

        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a recastBot whose identifier is specified in parameter
     * @param nKey The recastBot primary key
     * @return an instance of RecastBot
     */
    public static RecastBot findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin);
    }

    /**
     * Load the data of all the recastBot objects and returns them as a list
     * @return the list which contains the data of all the recastBot objects
     */
    public static List<RecastBot> getRecastBotsList( )
    {
        return _dao.selectRecastBotsList( _plugin );
    }
    
    /**
     * Load the id of all the recastBot objects and returns them as a list
     * @return the list which contains the id of all the recastBot objects
     */
    public static List<Integer> getIdRecastBotsList( )
    {
        return _dao.selectIdRecastBotsList( _plugin );
    }
    
    /**
     * Load the data of all the recastBot objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the recastBot objects
     */
    public static ReferenceList getRecastBotsReferenceList( )
    {
        return _dao.selectRecastBotsReferenceList(_plugin );
    }
}

