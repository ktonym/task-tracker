Ext.define('TTT.model.Project', {
    extend: 'Ext.data.Model',
    
    fields: [
        { name: 'idProject', type: 'int' },
        { name: 'projectName', type: 'string' },
        { name: 'idCompany', type: 'int' },
        { name: 'companyName', type: 'string' }

    ]
});
