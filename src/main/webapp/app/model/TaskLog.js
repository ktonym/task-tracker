Ext.define('TTT.model.TaskLog', {
    extend: 'Ext.data.Model',
    
    fields: [
        { name: 'idTaskLog', type: 'int' },
        { name: 'taskDescription', type: 'string' },
        { name: 'taskLogDate', type: 'date' },
        { name: 'taskMinutes', type: 'int' },
        { name: 'hours', type: 'float' },
        { name: 'username', type: 'string' },
        { name: 'userFullName', type: 'string' },
        { name: 'idTask', type: 'int' },
        { name: 'taskName', type: 'string' },
        { name: 'idProject', type: 'int' },
        { name: 'projectName', type: 'string' },
        { name: 'idCompany', type: 'int' },
        { name: 'companyName', type: 'string' }

    ]
});
