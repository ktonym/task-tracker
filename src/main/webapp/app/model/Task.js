Ext.define('TTT.model.Task', {
    extend: 'Ext.data.Model',
    
    fields: [
        { name: 'idTask', type: 'int' },
        { name: 'taskName', type: 'string' },
        { name: 'idProject', type: 'int' },
        { name: 'projectName', type: 'string' },
        { name: 'idCompany', type: 'int' },
        { name: 'companyName', type: 'string' }

    ]
});
