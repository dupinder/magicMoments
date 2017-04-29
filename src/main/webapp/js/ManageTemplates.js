var compiledTemplates = new Object();

function compileAllTemplates(){
	var templates = $('script[type="text/x-handlebar-template"]');
	compileHandlebarTemplate(templates);
}

function compileTemplate(htmlContent){
	var div = document.createElement("div");
	div.innerHTML = htmlContent;
	
	var templates = $(div).find('script[type="text/x-handlebar-template"]');
	compileHandlebarTemplate(templates);
	
	return $(templates).attr('id');
}

function compileHandlebarTemplate(templates){
	for(template of templates)
	{
		let compiledTemplate = Handlebars.compile($(template).html());
		compiledTemplates[$(template).attr('id')] = compiledTemplate;
	}
}

/*
container: Element into which content will be loaded
templateId: Id of templated to be loaded
context: data to be loaded for template
*/
function loadTemplate(container, templateId, context, method){
	let template = compiledTemplates[templateId];
	if(template != undefined)
	{
		var html = template(context);
		container.html(html);
	}
}