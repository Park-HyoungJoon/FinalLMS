export const login = (a,b,c) => ({type:"login",grantType:a,accessToken:b,tokenExpiresIn:c});
export const logout = () => ({type:"logout",grantType:'',accessToken:'',tokenExpiresIn:''});

const token = {
    grantType : '',
    accessToken : '',
    tokenExpiresIn : '',
}
const reducer = (state=token,action) => {
    switch(action.type){
        case "login":
            return {accessToken: action.text};
        case "logout":
            return {accessToken:''};
        default:
            return state;
    }
}
export default reducer;