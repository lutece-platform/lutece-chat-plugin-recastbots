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

package fr.paris.lutece.plugins.recastbots.service;

import fr.paris.lutece.plugins.chatbot.business.BotPost;
import fr.paris.lutece.plugins.chatbot.service.bot.ChatBot;
import fr.paris.lutece.plugins.recast.business.DialogResponse;
import fr.paris.lutece.plugins.recast.business.Message;
import fr.paris.lutece.plugins.recast.service.BotMessageRenderer;
import fr.paris.lutece.plugins.recast.service.RecastDialogService;
import fr.paris.lutece.plugins.recast.service.RenderersMap;
import fr.paris.lutece.plugins.recastbots.business.RecastBot;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.httpaccess.HttpAccessException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * BotInstance
 */
public class BotInstance implements ChatBot
{
    private static final String DEFAULT_AVATAR = "images/skin/plugins/recastbots/default_avatar.png";
    private static final String BEAN_RENDERERS_MAP = "recastbots.mapRenderers";

    private String _strKey;
    private String _strName;
    private String _strDescription;
    private String _strAvatarUrl;
    private String _strToken;
    private String _strLanguage;
    private boolean _bStandalone;
    private String _strWelcomeMessage;
    private Map<String, BotMessageRenderer> _mapRenderers;

    /**
     * Constructor
     * 
     * @param bot
     *            The Recast bot data
     */
    public BotInstance( RecastBot bot )
    {
        _strKey = bot.getBotKey( );
        _strName = bot.getName( );
        _strDescription = bot.getDescription( );
        _strLanguage = bot.getLanguage( );
        _strAvatarUrl = bot.getAvatarUrl( );
        _strToken = bot.getToken( );
        _bStandalone = bot.getStandalone( ) != 0;
        _strWelcomeMessage = bot.getWelcomeMessage( );

        RenderersMap renderers = SpringContextService.getBean( BEAN_RENDERERS_MAP );
        _mapRenderers = renderers.getMap( );

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getKey( )
    {
        return _strKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getName( Locale locale )
    {
        return _strName;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getDescription( Locale locale )
    {
        return _strDescription;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<String> getAvailableLanguages( )
    {
        List<String> listLanguages = new ArrayList<>( );
        listLanguages.add( _strLanguage );
        return listLanguages;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getAvatarUrl( )
    {
        if ( ( _strAvatarUrl == null ) || ( _strAvatarUrl.equals( "" ) ) )
        {
            return DEFAULT_AVATAR;
        }
        return _strAvatarUrl;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<BotPost> processUserMessage( String strMessage, String strConversationId, Locale locale )
    {
        List<BotPost> listMessages = new ArrayList<>( );
        DialogResponse response = null;

        String strMessageForBot = extractMessageForBot( strMessage );

        try
        {
            response = RecastDialogService.getDialogResponse( strMessageForBot, strConversationId, _strToken, _strLanguage );
        }
        catch( IOException | HttpAccessException ex )
        {
            AppLogService.error( "Error accessing recast API : " + ex.getMessage( ), ex );
        }

        if ( response != null )
        {
            for ( Message message : response.getMessages( ) )
            {
                String strContent = message.getContent( _mapRenderers );
                String strContentType = message.getType( );
                BotPost post = new BotPost( strContent, strContentType );
                listMessages.add( post );
            }
        }
        return listMessages;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isStandalone( )
    {
        return _bStandalone;
    }

    /**
     * Sets the Standalone
     *
     * @param bStandalone
     *            The Standalone
     */
    public void setStandalone( boolean bStandalone )
    {
        _bStandalone = bStandalone;
    }

    /**
     * Returns the WelcomeMessage
     * 
     * @return The WelcomeMessage
     */
    @Override
    public String getWelcomeMessage( )
    {
        return _strWelcomeMessage;
    }

    /**
     * Sets the WelcomeMessage
     * 
     * @param strWelcomeMessage
     *            The WelcomeMessage
     */
    public void setWelcomeMessage( String strWelcomeMessage )
    {
        _strWelcomeMessage = strWelcomeMessage;
    }

    /**
     * Extract from the message the
     * 
     * @param strMessage
     *            The input message
     * @return The output message
     */
    private String extractMessageForBot( String strMessage )
    {
        int nPos = strMessage.indexOf( '|' );
        if ( nPos > 0 )
        {
            return strMessage.substring( nPos + 1 );
        }
        return strMessage;
    }

    /**
     * Reset the conversation
     * @param strConversationId The conversation ID
     */
    @Override
    public void reset( String strConversationId )
    {
        
    }

}
