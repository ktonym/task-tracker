Ext.define('TTT.Application', {
    name: 'TTT',
    extend: 'Ext.app.Application',
    requires: ['TTT.view.Viewport','TTT.view.LogonWindow'],
    models:  ['User'],
    controllers: ['MainController','UserController'],
    stores: ['User'],

    init: function(application){
        TTT.URL_PREFIX ='ttt/';
        Ext.Ajax.on('beforerequest', function(conn,options,eOpts){
            options.url = TTT.URL_PREFIX + options.url;
        });
    },
    launch: function() {
        var me = this;
        TTT.console = function(output){
            if (typeof console !== 'undefined') {
                console.info(output);
            }
        };
        me.logonWindow = Ext.create('TTT.view.LogonWindow');
        me.logonWindow.show();
    },
    doAfterLogon: function(userObj){
         TTT.console(userObj);
        var me = this;
        me.getUser = function(){
            return userObj;
        };
        me.isAdmin = function(){
            return userObj.adminRole === 'Y';
        };
        Ext.create('TTT.view.Viewport');
        me.logonWindow.hide();
    }
});
