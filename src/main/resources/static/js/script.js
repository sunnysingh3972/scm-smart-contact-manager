// console.log("he;llo");
let currentTheme=getTheme();
// console.log(currentTheme);
document.addEventListener("DOMContentLoaded",()=>{
    changeTheme()
})

function changeTheme(){
    //set to web page
    document.querySelector("html").classList.add(currentTheme);
    //set the listner to chnage the theme
    const chnageThemeButton=document.querySelector("#theme_change_button");
    
    chnageThemeButton.addEventListener("click",(event)=>{
        // console.log("clicked");
        document.querySelector('html').classList.remove(currentTheme);
        if(currentTheme==="dark"){
            currentTheme="light";
            
        }
        else{
            currentTheme="dark";
        }
        //local storage me update krege
        setTheme(currentTheme);
        document.querySelector('html').classList.add(currentTheme);
        chnageThemeButton.querySelector("span").textContent=currentTheme=="light"?"Dark":"Light";
       
    })
}
function setTheme(theme){
    localStorage.setItem("theme", theme);
}
function getTheme(){
    let theme = localStorage.getItem("theme");

    return theme?theme:"light";
}
