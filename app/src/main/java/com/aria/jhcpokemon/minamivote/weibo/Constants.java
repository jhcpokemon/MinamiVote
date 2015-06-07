package com.aria.jhcpokemon.minamivote.weibo;

/**
 * Created by jhcpokemon on 06/07/15.
 */
public interface Constants {
    String APP_KEY = "3073917926";
    String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    String SCOPE = "email,direct_messages_read,direct_messages_write,"
            +"friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            +"follow_app_official_microblog," + "invitation_write";
}
