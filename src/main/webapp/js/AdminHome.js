
$(document).ready(function(){
	showSpinner(true);
	contentLoader = $('.content-holder');
	compileAllTemplates();
});

function showSpinner(show){
	if(show)
		$('.spinner-main').css('width', '100%');
	else
		$('.spinner-main').css('width', '0');
}

function showDropdownOptions(selector){
	$('.dropdown-option').hide();
	selector.find('.dropdown-option').show();
}

function showCreateEventView(){
	getAllRequiredData();
}

function showPanel(target, source, itemToDisable){
	var toDisable = false;
	if($(source).is(':checked'))
	{
		toDisable = true;
		$(target).show('slow');
	}
	else
	{
		resetPanelData(target, source, itemToDisable);
		return;
	}
	
	if(itemToDisable !== undefined)
	{
		if(Array.isArray(itemToDisable))
		{
			for(ele of itemToDisable)
			{
				$(ele).prop('disabled', toDisable);
				$(ele).val(-1);
			}
		}	
		else
		{
			$(itemToDisable).prop('disabled', toDisable);
			$(itemToDisable).val(-1);
		}	
	}	
}

function resetPanelData(target, source, itemToDisable){
	$(target).hide('slow');
	$(target).find('input[type=text]').val("");
	$(target).find('textarea').val("");
	
	$(source).prop('checked', false)
	
	if(itemToDisable !== undefined)
	{
		if(Array.isArray(itemToDisable))
			for(ele of itemToDisable)
				$(ele).prop('disabled', false);
		else
			$(itemToDisable).prop('disabled', false);
	}	
}

function resetCreateEventFields(){
	var parent = $('.create-event-parent');
	parent.find('input[type=text], textarea').val('');
	parent.find('input[type=checkbox]').prop('checked', false);
	parent.find('.expandable-panel').hide('slow');
	parent.find('select.college-list').val(-1);
	parent.find('select.branch-list').val(-1);
}

function updateFilePath(){
	$('.import-inviters').val($('#importfile').val());
}


function createCollege(){
	var parent = $('.create-event-parent .college-panel');
	var name = parent.find('.college-name').val();
	var address = parent.find('.college-address').val();
	var abbreviation = parent.find('.college-abbreviation').val();
	var pincode = parent.find('.pincode').val();
	
	$.ajax({
		url: 'CreateCollege',
		type: 'POST',
		data: {collegeName: name, address: address, abbreviation: abbreviation, pincode: pincode},
		success: function(response){
			response = JSON.parse(response);
			var result = response['result'];
			if(result == "true")
			{
				var existingCollegeIds = [];
				for(option of $('select.college-list option'))
				{
					existingCollegeIds.push($(option).attr('value'));
				}	
				
				var colleges = JSON.parse(response['dataCollageList']);
				var $selector = $('select.college-list');
				var idToSelect;
				for(college of colleges)
				{
					if(existingCollegeIds.indexOf(college.id+"") != -1)
						continue;
					
					var element = document.createElement('option');
					idToSelect = college.id; 
					createDOMForDropdowns(college.id, college.name + "  " + college.abbreviation, element);
					$selector.append(element);
				}	
				
				if(idToSelect !== undefined)
					$('select.college-list').val(idToSelect);
				$('.cancel-create-college').click();
			}	
			else
			{
				var cause = response['cause'];
			}
		},
		failure: function(error){
			
		},
	});
}

function createBranch(){
	var parent = $('.create-event-parent .branch-panel');
	var name = parent.find('.branch-name').val();
	var abbreviation = parent.find('.branch-abbreviation').val();
	var collegeId = $('select.college-list').val();
	$.ajax({
		url: 'CreateBranch',
		type: 'POST',
		data: {name: name, abbreviation: abbreviation, collegeId: collegeId},
		success: function(response){
			response = JSON.parse(response);
			var result = response['result'];
			if(result == "true")
			{
				var existingBranchIds = [];
				for(option of $('select.branch-list option'))
				{
					existingBranchIds.push($(option).attr('value'));
				}	
				
				var branches = JSON.parse(response['branchList']);
				var $selector = $('select.branch-list');
				var idToSelect;
				for(branch of branches)
				{
					if(existingBranchIds.indexOf(branch.id+"") != -1)
						continue;
					
					var element = document.createElement('option');
					idToSelect = branch.id; 
					createDOMForDropdowns(branch.id, branch.branchName + "  " + branch.branchAbbrivation, element);
					$selector.append(element);
				}	
				
				if(idToSelect !== undefined)
					$('select.branch-list').val(idToSelect);
				$('.cancel-create-branch').click();
			}	
			else
			{
				var cause = response['cause'];
			}
		},
		failure: function(error){
			
		},
	});
}

function createEventM(){
//	User below commented code when UI side validations are applied
//	var parent = $('.create-event-parent');
//	var eventName = parent.find('.eventname').val();
//	var eventDesc = parent.find('.event-desc').val();
//	var eventPictureFolderId = parent.find('.event-picture-folder-id').val();
//	
//	var eventStartDate = parent.find('.form_datetime.start-date input').val();
//	var eventEndDate = parent.find('.form_datetime.end-date input').val();
//	var validityPeriod = parent.find('.form_datetime.validity-date input').val();
//	
//	var selectedCollege = parent.find('select.college-list').val();
//	var selectedBranch = parent.find('select.branch-list').val();
//	
//	var file = document.getElementById('importfile').files[0];
	
	$('#createEventForm').submit();
}

var eventData;

function getAllRequiredData(){
	showSpinner(true);
	$.ajax({
		url: 'GetAllRequiredDataForEvents',
		type: 'GET',
		success: function(response){
			showSpinner(false);
			response = JSON.parse(response);
			let allColleges = response['allColleges'];
			let allBranches = response['allBranches'];
			
			var allCollegesProto = [];
			if(allColleges !== undefined)
			{
				for(college of allColleges)
				{
					var ci = new CollegeInfo(college.id, college.name, college.abbreviation);
					allCollegesProto.push(ci)
				}	
			}	
			
			var allBranchesProto = [];
			if(allBranches !== undefined)
			{
				for(branch of allBranches)
				{
					var bi = new BranchInfo(branch.id, branch.branchName, branch.branchAbbrivation, branch.collegeId);
					allBranchesProto.push(bi);
				}
			}	
			eventData = new EventData(allCollegesProto, allBranchesProto);
			loadTemplate(contentLoader, 'create-event', JSON.parse(eventData.jsonData()));
			$('select.branch-list').prop('disabled', true);
			$(".form_datetime").datepicker({'autoclose': true, 'format': 'dd/mm/yyyy'});
		},
		failure: function(error){
			showSpinner(false);
		}
	});
}

function EventData(colleges, branches){
	this.allColleges = colleges;
	this.allBranches = branches;
	this.jsonData = () => JSON.stringify(this);
}

function CollegeInfo(id, name, abbreviation){
	this.id = id;
	this.name = name;
	this.abbreviation = abbreviation;
}

function BranchInfo(id, name, abbreviation, collegeId){
	this.id = id;
	this.name = name;
	this.abbreviation = abbreviation;
	this.collegeId = collegeId;
}

function showBranchesForSelectedCollege(ele){
	var selectedCollegeId = $(ele).val();
	var $selector = $('select.branch-list');
	$selector.val(-1);
	if(selectedCollegeId == "-1")
	{
		$selector.prop('disabled', true);
		return;
	}
	
	for(branch of eventData.allBranches)
	{
		if(branch.collegeId != selectedCollegeId)
			continue;
		
		var element = document.createElement('option');
		createDOMForDropdowns(branch.id, branch.name + " " + branch.abbreviation, element);
		$selector.append(element);
	}
	
	$selector.prop('disabled', false);
}

function createDOMForDropdowns(value, text, elementToAppend){
	elementToAppend.setAttribute('value', value);
	var text = document.createTextNode(text);
	elementToAppend.appendChild(text);
}