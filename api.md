##REST API

###Default templates 
GET     /api/1/defaultTemplates
* get a list of all default templates

GET     /api/1/defaultTemplate/:templateId
* get a default template

POST    /api/1/defaultTemplate
* create a new default template

PUT     /api/1/defaultTemplate/:templateId
* update any existing default template

DELETE  /api/1/defaultTemplate/:templateId
* delete any existing default template

###User templates
GET     /api/1/userTemplates
* get a list of your user templates

GET     /api/1/userTemplate/:templateId
* get a user template

POST    /api/1/userTemplate
* create a new user template

PUT     /api/1/userTemplate/:templateId
* update your existing template

DELETE  /api/1/userTemplate/:templateId
* delete your existing template

###Apps
GET     /api/1/apps
* get a list all apps

POST    /api/1/app
* create a new app

PUT     /api/1/app/:appId
* update an existing app

DELETE  /api/1/app/:appId
* delete an existing app

##Data Model    

defaultTemplate
{
    defaultTemplateId: string,
    templateName: string,
    json: string,
    enabled: boolean,
}
    
template - 
{
    userId: string,
    userTemplateId: string,
    templateName: string,
    json: string,
}

app {
    appId: string,
    appName: string,
    url: string,
    logo: string,
    enabled: boolean,
}
