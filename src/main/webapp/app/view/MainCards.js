Ext.define("TTT.view.MainCards", {
    extend: 'Ext.container.Container',
    xtype: 'maincards',
    requires: ['Ext.layout.container.Card','TTT.view.Welcome',
        'TTT.view.user.ManageUsers', 'TTT.view.tasklog.ManageTaskLogs'],
    layout: 'card',
    initComponent: function(){
        var me = this;
        Ext.applyIf(me,{
            items: [{
                xtype: 'welcome',
                itemId: 'welcomeCard'
            },{
                xtype: 'manageusers',
                itemId: 'manageUsersCard'
            },{
                xtype: 'managetasklogs',
                itemId: 'taskLogCard'
            }]
        });
        me.callParent(arguments);
    }
});