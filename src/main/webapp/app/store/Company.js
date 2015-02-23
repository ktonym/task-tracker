Ext.define('TTT.store.Company',{
    extend: 'Ext.data.Store',
    requires: ['TTT.model.Company'],
    model: 'TTT.model.Company',
    proxy: {
        type: 'ajax',
        url: 'company/treenode.json',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
});
