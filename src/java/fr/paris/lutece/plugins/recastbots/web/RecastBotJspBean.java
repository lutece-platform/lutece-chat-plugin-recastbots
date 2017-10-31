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
 	
package fr.paris.lutece.plugins.recastbots.web;

import fr.paris.lutece.plugins.recastbots.business.RecastBot;
import fr.paris.lutece.plugins.recastbots.business.RecastBotHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage RecastBot features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageRecastBots.jsp", controllerPath = "jsp/admin/plugins/recastbots/", right = "RECASTBOTS_MANAGEMENT" )
public class RecastBotJspBean extends AbstractManageBotsJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_RECASTBOTS = "/admin/plugins/recastbots/manage_recastbots.html";
    private static final String TEMPLATE_CREATE_RECASTBOT = "/admin/plugins/recastbots/create_recastbot.html";
    private static final String TEMPLATE_MODIFY_RECASTBOT = "/admin/plugins/recastbots/modify_recastbot.html";

    // Parameters
    private static final String PARAMETER_ID_RECASTBOT = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_RECASTBOTS = "recastbots.manage_recastbots.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_RECASTBOT = "recastbots.modify_recastbot.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_RECASTBOT = "recastbots.create_recastbot.pageTitle";

    // Markers
    private static final String MARK_RECASTBOT_LIST = "recastbot_list";
    private static final String MARK_RECASTBOT = "recastbot";

    private static final String JSP_MANAGE_RECASTBOTS = "jsp/admin/plugins/recastbots/ManageRecastBots.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_RECASTBOT = "recastbots.message.confirmRemoveRecastBot";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "recastbots.model.entity.recastbot.attribute.";

    // Views
    private static final String VIEW_MANAGE_RECASTBOTS = "manageRecastBots";
    private static final String VIEW_CREATE_RECASTBOT = "createRecastBot";
    private static final String VIEW_MODIFY_RECASTBOT = "modifyRecastBot";

    // Actions
    private static final String ACTION_CREATE_RECASTBOT = "createRecastBot";
    private static final String ACTION_MODIFY_RECASTBOT = "modifyRecastBot";
    private static final String ACTION_REMOVE_RECASTBOT = "removeRecastBot";
    private static final String ACTION_CONFIRM_REMOVE_RECASTBOT = "confirmRemoveRecastBot";

    // Infos
    private static final String INFO_RECASTBOT_CREATED = "recastbots.info.recastbot.created";
    private static final String INFO_RECASTBOT_UPDATED = "recastbots.info.recastbot.updated";
    private static final String INFO_RECASTBOT_REMOVED = "recastbots.info.recastbot.removed";
    
    // Session variable to store working values
    private RecastBot _recastbot;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_RECASTBOTS, defaultView = true )
    public String getManageRecastBots( HttpServletRequest request )
    {
        _recastbot = null;
        List<RecastBot> listRecastBots = RecastBotHome.getRecastBotsList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_RECASTBOT_LIST, listRecastBots, JSP_MANAGE_RECASTBOTS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_RECASTBOTS, TEMPLATE_MANAGE_RECASTBOTS, model );
    }

    /**
     * Returns the form to create a recastbot
     *
     * @param request The Http request
     * @return the html code of the recastbot form
     */
    @View( VIEW_CREATE_RECASTBOT )
    public String getCreateRecastBot( HttpServletRequest request )
    {
        _recastbot = ( _recastbot != null ) ? _recastbot : new RecastBot(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_RECASTBOT, _recastbot );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_RECASTBOT, TEMPLATE_CREATE_RECASTBOT, model );
    }

    /**
     * Process the data capture form of a new recastbot
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_RECASTBOT )
    public String doCreateRecastBot( HttpServletRequest request )
    {
        populate( _recastbot, request );

        // Check constraints
        if ( !validateBean( _recastbot, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_RECASTBOT );
        }

        RecastBotHome.create( _recastbot );
        addInfo( INFO_RECASTBOT_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_RECASTBOTS );
    }

    /**
     * Manages the removal form of a recastbot whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_RECASTBOT )
    public String getConfirmRemoveRecastBot( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_RECASTBOT ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_RECASTBOT ) );
        url.addParameter( PARAMETER_ID_RECASTBOT, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_RECASTBOT, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a recastbot
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage recastbots
     */
    @Action( ACTION_REMOVE_RECASTBOT )
    public String doRemoveRecastBot( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_RECASTBOT ) );
        RecastBotHome.remove( nId );
        addInfo( INFO_RECASTBOT_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_RECASTBOTS );
    }

    /**
     * Returns the form to update info about a recastbot
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_RECASTBOT )
    public String getModifyRecastBot( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_RECASTBOT ) );

        if ( _recastbot == null || ( _recastbot.getId(  ) != nId ))
        {
            _recastbot = RecastBotHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_RECASTBOT, _recastbot );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_RECASTBOT, TEMPLATE_MODIFY_RECASTBOT, model );
    }

    /**
     * Process the change form of a recastbot
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_RECASTBOT )
    public String doModifyRecastBot( HttpServletRequest request )
    {
        populate( _recastbot, request );

        // Check constraints
        if ( !validateBean( _recastbot, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_RECASTBOT, PARAMETER_ID_RECASTBOT, _recastbot.getId( ) );
        }

        RecastBotHome.update( _recastbot );
        addInfo( INFO_RECASTBOT_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_RECASTBOTS );
    }
}