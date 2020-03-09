window.addEventListener("load", function(){

	function populateLocalStorage(){
	
		var da = new DataAccess();

		// clear out local storage...
		for (var key in localStorage){
			  	localStorage.setItem(key, null);
				localStorage.removeItem(key);
		}

		// insert 3 contact objects...
		da.insert({firstName:"Bob",	lastName: "Smith", email: "bob@smith.com", phone: "555-1212"});
		da.insert({firstName:"Betty", lastName: "Jones", email: "betty@jones.com", phone: "555-1234"});
		da.insert({firstName:"Sara", lastName: "Jackson", email: "sara@jackson.com", phone: "555-5555"});
	}

	var contactList = document.getElementById("contact-list");
	var btnBack = document.getElementById("btnBack");
	var btnAdd = document.getElementById("btnAdd");

	var contactDetails = document.getElementById("contact-details");
	var lblContactName = document.getElementById("lblContactName");
	var lblContactEmail = document.getElementById("lblContactEmail");
	var lblContactPhone = document.getElementById("lblContactPhone");
	var lblContactId = document.getElementById("lblContactId");
	var btnEdit = document.getElementById("btnEdit");
	var btnDelete = document.getElementById("btnDelete");

	var contactForm = document.getElementById("contact-form");
	var txtId = document.getElementById("txtId");
	var txtFirstName = document.getElementById("txtFirstName");
	var txtLastName = document.getElementById("txtLastName");
	var txtEmail = document.getElementById("txtEmail");
	var txtPhone = document.getElementById("txtPhone");
	var btnSave = document.getElementById("btnSave");
	var btnCancel = document.getElementById("btnCancel");

	var vFirstName = document.getElementById("vFirstName");
	var vLastName = document.getElementById("vLastName");
	var vEmail = document.getElementById("vEmail");
	var vPhone = document.getElementById("vPhone");

	var listView = document.getElementById("contact-list-container");
	var detailsView = document.getElementById("contact-details-container");
	var formView = document.getElementById("contact-form-container");

	var regexName = /^[a-z-'\s]{1,30}$/i;
	var regexPhone = /^[0-9-()\s]{7,14}$/;
	var regexEmail = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/i;

	var da = new DataAccess();
	//populateLocalStorage();
	showAllContacts();

	function showAllContacts(){

		showView(listView);

		var contacts = da.getAll();
		contactList.innerHTML = "";

		if(contacts.length > 0){
			for(var x = 0; x < contacts.length; x++){
				var c = contacts[x];
				var li = document.createElement("li");
				li.innerHTML = c.firstName + " " + c.lastName;
				li.setAttribute("contactId", c.id);
				contactList.appendChild(li);
			}
		}else{
			var h3 = document.createElement("h3");
			h3.innerHTML = "No contacts";
			contactList.appendChild(h3);
		}
	}
	
	contactList.addEventListener("click", function(evt){
		var selectedId = evt.target.getAttribute("contactId");
		var selectedContact = da.getById(selectedId);
		showContactDetails(selectedContact);
	});

	window.addEventListener("click", function(evt){
		console.log(evt.target);
	});

	function showContactDetails(contact){
		lblContactName.innerHTML = contact.firstName + " " + contact.lastName;
		lblContactEmail.innerHTML = contact.email;
		lblContactPhone.innerHTML = contact.phone;
		lblContactId.innerHTML = contact.id;
		showView(detailsView);
	}

	btnBack.addEventListener("click", function(evt){
		if(listView.style.zIndex == 1){
			evt.preventDefault();
		}else if(detailsView.style.zIndex == 1){
			showView(listView);
		}else if(formView.style.zIndex == 1){
			showView(detailsView);
		}
	});

	btnAdd.addEventListener("click", function(){
		txtId.value = "";
		txtFirstName.value = "";
		txtLastName.value = "";
		txtEmail.value = "";
		txtPhone.value = "";
		showView(formView);
	})

	btnEdit.addEventListener("click", function(){
		var id = lblContactId.innerHTML;
		var selectedContact = da.getById(id);
		editContact(selectedContact);
		showView(formView);
	});

	function editContact(contact){
		txtId.value = contact.id;
		txtFirstName.value = contact.firstName;
		txtLastName.value = contact.lastName;
		txtEmail.value = contact.email;
		txtPhone.value = contact.phone;
	}

	btnDelete.addEventListener("click", function(){
		var id = lblContactId.innerHTML;
		if(id > 0 && confirm("Are you sure you want to delete this contact?")){
			da.deleteById(id);
			showAllContacts();
		}
	});

	btnSave.addEventListener("click", function(evt){
		evt.preventDefault();
		if(validate()){
			var obj = {
				id: txtId.value,
				firstName: txtFirstName.value,
				lastName: txtLastName.value,
				email: txtEmail.value,
				phone: txtPhone.value
			};

			if(obj.id > 0){
				da.update(obj);
			}else{
				da.insert(obj);
			}
			showAllContacts();
		}
	});

	btnCancel.addEventListener("click", function(){
		showView(listView);
	});

	function validate(){
		
		vFirstName.textContent = "You must enter your first name";
		vLastName.textContent = "You must enter your last name";
		vPhone.textContent = "You must enter your phone number";
		vEmail.textContent = "You must enter your email address";

		var isValid = true;
		var firstInvalidField = null;

		if(txtFirstName.value == ""){
				isValid = false;
				vFirstName.style.visibility = "visible";
				firstInvalidField = txtFirstName;
		}else if(txtFirstName.value !== ""){
			if(!(regexName.test(txtFirstName.value))){
				isValid = false;
				vFirstName.textContent = "Invalid characters. Acceptable characters: letters, spaces, \"-\", \" ' \"";
				vFirstName.style.visibility = "visible";
				firstInvalidField = txtFirstName;
			}else{
				vFirstName.style.visibility = "hidden";
			}
		}

		if(txtLastName.value == ""){
				isValid = false;
				vLastName.style.visibility = "visible";
				if(firstInvalidField == null){
					firstInvalidField = txtLastName;
				}
		}else if(txtLastName.value !== ""){
			if(!(regexName.test(txtLastName.value))){
				isValid = false;
				vLastName.textContent = "Invalid characters. Acceptable characters: letters, spaces, \"-\", \" ' \"";
				vLastName.style.visibility = "visible";
				if(firstInvalidField == null){
					firstInvalidField = txtLastName;
				}
			}else{
				vLastName.style.visibility = "hidden";
			}
		}

		if(txtEmail.value == ""){
				isValid = false;
				vEmail.style.visibility = "visible";
				if(firstInvalidField == null){
					firstInvalidField = txtEmail;
				}
		}else if(txtEmail.value !== ""){
			if(!(regexEmail.test(txtEmail.value))){
				isValid = false;
				vEmail.textContent = "We don't recognize this email address. Hint: did you forget \"@\"? ";
				vEmail.style.visibility = "visible";
				if(firstInvalidField == null){
					firstInvalidField = txtEmail;
				}
			}else{
				vEmail.style.visibility = "hidden";
			}
		}
		
		if(txtPhone.value == ""){
				isValid = false;
				vPhone.style.visibility = "visible";
				if(firstInvalidField == null){
					firstInvalidField = txtPhone;
				}
		}else if(txtPhone.value !== ""){
			if(!(regexPhone.test(txtPhone.value))){
				isValid = false;
				vPhone.textContent = "You entered an invalid phone number. Valid format: \"xxx-xxx-xxxx\"";
				vPhone.style.visibility = "visible";
				if(firstInvalidField == null){
					firstInvalidField = txtPhone;
				}
			}else{
				vPhone.style.visibility = "hidden";
			}
		}

		if(firstInvalidField!= null){
			firstInvalidField.focus();	
		}
		return isValid;	
		
	}

	function showView(view){

		listView.style.opacity = 0;
		detailsView.style.opacity = 0;
		formView.style.opacity = 0;

		view.style.opacity = 1;

		listView.style.zIndex = 0;
		detailsView.style.zIndex = 0;
		formView.style.zIndex = 0;

		view.style.zIndex = 1;
	}
});