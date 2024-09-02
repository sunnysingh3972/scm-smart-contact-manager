console.log("admin");
// document.querySelector("#image_file_input").addEventListener("change", function(event){
//     let file=event.target.files[0];
//     let reader=new FileReader();
//     reader.onload=function(){
//         document.getElementById("upload_image_preview").src=reader.result;
        
       
//     }
//     reader.readAsDataURL(file);  
// })

const imagePreview = document.getElementById("upload_image_preview");
        
        document.getElementById("image_file_input").addEventListener("change", function(event) {
            const [file] = event.target.files;
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    imagePreview.src = e.target.result;
                    imagePreview.style.display = "block";
                    imagePreview.classList.remove("opacity-50");
                }
                reader.readAsDataURL(file);
            } else {
                imagePreview.style.display = "none";
                imagePreview.classList.add("opacity-50");
            }
        });