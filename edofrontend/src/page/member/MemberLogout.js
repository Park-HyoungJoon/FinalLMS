import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux/es/hooks/useDispatch';
import {useNavigate} from 'react-router-dom';
function MemberLogout(props) {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    useEffect(() => {
        dispatch({type:"logout"});
        navigate('/');
    },[])
    return (
        <div>
            
        </div>
    );
}

export default MemberLogout;