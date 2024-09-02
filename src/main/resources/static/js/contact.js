const viewContactModal=document.getElementById("view_contact_modal");
const baseUrl="http://localhost:8080";
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: "view_contact_modal",
  override: true
};

const modal=new Modal(viewContactModal,options,instanceOptions);
function openContactModal(){
    modal.show();
}
function closeContactModal(){
    modal.hide();
}

async function loadContactData(id){
    console.log(id);
    try {
        const data=await(await fetch(`${baseUrl}/api/contacts/${id}`)).json(); 
        console.log(data);
        document.getElementById("contact_name").innerHTML=data.name;
        openContactModal();
        document.getElementById("contact_email").innerHTML=data.email;
        if(!data.picture)
        document.getElementById("contact_image").src="/images/download.png";
        else { 
        document.getElementById("contact_image").src=data.picture;} 
        document.getElementById("contact_address").innerHTML=data.address;
        if(!data.description){
            document.getElementById("contact_description").innerHTML="Not mentioned";
        }else{
            document.getElementById("contact_description").innerHTML=data.description;
        }
       
        document.getElementById("contact_phone").innerHTML=data.phoneNumber;
       const contactFavourite= document.getElementById("contact_favourite");
       if(data.favorite){
        contactFavourite.innerHTML="<i  class='fas fa-star text-yellow-400'></i><i  class='fas fa-star text-yellow-400'></i><i  class='fas fa-star text-yellow-400'></i><i  class='fas fa-star text-yellow-400'></i><i  class='fas fa-star text-yellow-400'></i>"; 
       }else{
        contactFavourite.innerHTML="Not a Favorite Contact";
       }
       const contactWebsite=document.getElementById("contact_website");
       if(!data.websiteLink){
        contactWebsite.innerHTML="Not mentioned";
       }else{
        contactWebsite.href=data.websiteLink;
       }
       contactLinkdin=document.getElementById("contact_linkdin");
       if(!data.linkedInLink){
        contactLinkdin.innerHTML="Not mentioned";
       }else{
        contactLinkdin.href=data.linkedInLink;
       }
       
    } catch (error) {
        console.log("error"+error);
        
    }

    // const fetch(`http://localhost:8080/api/contacts/${id}`).then(async(response) =>{
    //     const data=await response.json();
    //     console.log(data);
    //     return data;
    // }).catch((error) =>{
    //     console.log(error);
    // })
    // console.log("Loading contact data");
}

 //dlete contact

 async function deleteContact(id){
    Swal.fire({
        title: "Do you want to delete the contact?",
        icon:"warning",
        showCancelButton: true,
        confirmButtonText: "Delete",
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            const url=`${baseUrl}/user/contacts/delete/`+id;
            window.location.replace(url);
        } else if (result.isDenied) {
          Swal.fire("Changes are not saved", "", "info");
        }
      });
 }