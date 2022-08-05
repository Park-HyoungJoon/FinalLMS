export const login = () => ({type:"login"});
export const logout = () => ({type:"logout"});

const logged = {
    logState : "logout",
}

const reducer = (state = logged,action) => {
    switch(action.type){
        case "login":
            return {logState : "login"};
        case "logout":
            return {logState : "logout"};
        default:
            return state;
    }
}
export default reducer;