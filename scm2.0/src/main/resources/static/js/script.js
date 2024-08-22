console.log("Script loaded");


let currenrTheme = getTheme();

document.addEventListener('DOMContentLoaded',()=>{
    changeTheme(currenrTheme);
})


function changeTheme() {
    changePageTheme(currenrTheme,currenrTheme);
    // se the listener to change theme button
    const changeThemeButton=document.querySelector('#theme_change_button');
    //get the current theme    
    let oldTheme=currenrTheme;
    changeThemeButton.addEventListener("click",(event)=>{
        console.log("change theme button clicked");
        const oldTheme=currenrTheme;
        if(currenrTheme === "dark") {
            currenrTheme="light";
        }
        else {
            currenrTheme="dark";
        }
        console.log(currenrTheme);
        changePageTheme(currenrTheme,oldTheme);
    });
}

// Set theme to localstorage
function setTheme(theme) {
    localStorage.setItem("theme",theme);
}

// get theme from localstorage
function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}

//change current page theme
function changePageTheme(theme,oldTheme) {
    //localstorage update
    setTheme(currenrTheme);
    //current theme remove
    if(oldTheme) {
        document.querySelector('html').classList.remove(oldTheme);
    }
    //set the current theme
    document.querySelector('html').classList.add(theme);
    //change the text of button
    document
        .querySelector("#theme_change_button")
        .querySelector('span').textContent=theme=="light" ? "Dark" : "Light";
}