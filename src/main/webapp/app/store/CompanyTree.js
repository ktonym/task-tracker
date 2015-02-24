Ext.define('TTT.store.CompanyTree',{
   extend: 'Ext.data.TreeStore',
    proxy: {
        type: 'ajax',
        url: 'company/treenode.json'
    }
});
