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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for RecastBot objects
 */
public final class RecastBotDAO implements IRecastBotDAO
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_recast_bot, bot_key, name, description, avatar_url, language, bot_status, token, is_standalone, welcome_message FROM recastbots_bot WHERE id_recast_bot = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO recastbots_bot ( bot_key, name, description, avatar_url, language, bot_status, token, is_standalone, welcome_message ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM recastbots_bot WHERE id_recast_bot = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE recastbots_bot SET id_recast_bot = ?, bot_key = ?, name = ?, description = ?, avatar_url = ?, language = ?, bot_status = ?, token = ?, is_standalone = ?, welcome_message = ? WHERE id_recast_bot = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_recast_bot, bot_key, name, description, avatar_url, language, bot_status, token, is_standalone, welcome_message FROM recastbots_bot";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_recast_bot FROM recastbots_bot";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( RecastBot recastBot, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin );
        try
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, recastBot.getBotKey( ) );
            daoUtil.setString( nIndex++, recastBot.getName( ) );
            daoUtil.setString( nIndex++, recastBot.getDescription( ) );
            daoUtil.setString( nIndex++, recastBot.getAvatarUrl( ) );
            daoUtil.setString( nIndex++, recastBot.getLanguage( ) );
            daoUtil.setInt( nIndex++, recastBot.getBotStatus( ) );
            daoUtil.setString( nIndex++, recastBot.getToken( ) );
            daoUtil.setInt( nIndex++, recastBot.getStandalone( ) );
            daoUtil.setString( nIndex++, recastBot.getWelcomeMessage( ) );
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                recastBot.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
        finally
        {
            daoUtil.free( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public RecastBot load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery( );
        RecastBot recastBot = null;

        if ( daoUtil.next( ) )
        {
            recastBot = new RecastBot( );
            int nIndex = 1;

            recastBot.setId( daoUtil.getInt( nIndex++ ) );
            recastBot.setBotKey( daoUtil.getString( nIndex++ ) );
            recastBot.setName( daoUtil.getString( nIndex++ ) );
            recastBot.setDescription( daoUtil.getString( nIndex++ ) );
            recastBot.setAvatarUrl( daoUtil.getString( nIndex++ ) );
            recastBot.setLanguage( daoUtil.getString( nIndex++ ) );
            recastBot.setBotStatus( daoUtil.getInt( nIndex++ ) );
            recastBot.setToken( daoUtil.getString( nIndex++ ) );
            recastBot.setStandalone( daoUtil.getInt( nIndex++ ) );
            recastBot.setWelcomeMessage( daoUtil.getString( nIndex++ ) );
        }

        daoUtil.free( );
        return recastBot;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( RecastBot recastBot, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;

        daoUtil.setInt( nIndex++, recastBot.getId( ) );
        daoUtil.setString( nIndex++, recastBot.getBotKey( ) );
        daoUtil.setString( nIndex++, recastBot.getName( ) );
        daoUtil.setString( nIndex++, recastBot.getDescription( ) );
        daoUtil.setString( nIndex++, recastBot.getAvatarUrl( ) );
        daoUtil.setString( nIndex++, recastBot.getLanguage( ) );
        daoUtil.setInt( nIndex++, recastBot.getBotStatus( ) );
        daoUtil.setString( nIndex++, recastBot.getToken( ) );
        daoUtil.setInt( nIndex++, recastBot.getStandalone( ) );
        daoUtil.setString( nIndex++, recastBot.getWelcomeMessage( ) );
        daoUtil.setInt( nIndex, recastBot.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<RecastBot> selectRecastBotsList( Plugin plugin )
    {
        List<RecastBot> recastBotList = new ArrayList<RecastBot>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            RecastBot recastBot = new RecastBot( );
            int nIndex = 1;

            recastBot.setId( daoUtil.getInt( nIndex++ ) );
            recastBot.setBotKey( daoUtil.getString( nIndex++ ) );
            recastBot.setName( daoUtil.getString( nIndex++ ) );
            recastBot.setDescription( daoUtil.getString( nIndex++ ) );
            recastBot.setAvatarUrl( daoUtil.getString( nIndex++ ) );
            recastBot.setLanguage( daoUtil.getString( nIndex++ ) );
            recastBot.setBotStatus( daoUtil.getInt( nIndex++ ) );
            recastBot.setToken( daoUtil.getString( nIndex++ ) );
            recastBot.setStandalone( daoUtil.getInt( nIndex++ ) );
            recastBot.setWelcomeMessage( daoUtil.getString( nIndex++ ) );

            recastBotList.add( recastBot );
        }

        daoUtil.free( );
        return recastBotList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdRecastBotsList( Plugin plugin )
    {
        List<Integer> recastBotList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            recastBotList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return recastBotList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectRecastBotsReferenceList( Plugin plugin )
    {
        ReferenceList recastBotList = new ReferenceList( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            recastBotList.addItem( daoUtil.getInt( 1 ), daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return recastBotList;
    }
}
