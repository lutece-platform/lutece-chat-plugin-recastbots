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

import fr.paris.lutece.plugins.chatbot.service.BotService;
import fr.paris.lutece.plugins.chatbot.service.bot.ChatBot;
import fr.paris.lutece.plugins.recastbots.business.RecastBot;
import fr.paris.lutece.plugins.recastbots.business.RecastBotHome;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.util.ReferenceList;
import java.util.Locale;

/**
 * BotRegistrationService
 */
public class BotRegistrationService
{

    private static final String MESSAGE_STATUS_ENABLED = "recastbots.bot_status.enabled";
    private static final String MESSAGE_STATUS_DISABLED = "recastbots.bot_status.disabled";
    private static final int STATUS_ENABLED = 1;
    private static final int STATUS_DISABLED = 0;

    private static ReferenceList _listStatus;

    /**
     * Register all bots
     */
    public static void registerAllBots()
    {
        for( RecastBot bot : RecastBotHome.getRecastBotsList() )
        {
            ChatBot chatbot = new BotInstance( bot );
            BotService.register( chatbot );
        }
    }

    /**
     * Returns the list of available bot status
     *
     * @param locale The locale
     * @return The list
     */
    public static ReferenceList getBotsStatusList( Locale locale )
    {
        if( _listStatus == null )
        {
            _listStatus = new ReferenceList();
            _listStatus.addItem( STATUS_DISABLED, I18nService.getLocalizedString( MESSAGE_STATUS_DISABLED, locale ) );
            _listStatus.addItem( STATUS_ENABLED, I18nService.getLocalizedString( MESSAGE_STATUS_ENABLED, locale ) );
        }
        return _listStatus;
    }
}
