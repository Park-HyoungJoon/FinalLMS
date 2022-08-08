import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useDispatch } from 'react-redux/es/exports';
import { login } from '../../store';

const MemberLogin = props => {
  // 로그인을 위한 useState
  const [user, setUser] = useState({
    Email: '',
    password: '',
  });
  const dispatch = useDispatch();

  const [token,setToken] = useState({
    grantType : '',
    accessToken : '',
    tokenExpiresIn : '',
  });
  const Login = e => {
    e.preventDefault();
    fetch('http://localhost:80/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json; charset=utf-8' },
      body: JSON.stringify(user),
    })
      .then(
        res => res.json()//응답(res)이 오면 걔를 javascript object로 바꿈
    ).then(
        res=>{
            console.log(1,res);
            setToken(res);
            console.log(res.grantType);
            dispatch({type:"login",text:res.accessToken});
        }
      );
  };

  const changeValue = e => {
    setUser({
      ...user,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <div>
      <>
        {/* button누르면 login 실행dds */}
        <Form onSubmit={Login}>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="Email"
              placeholder="이메일주소"
              name="email"
              onChange={changeValue}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>password</Form.Label>
            <Form.Control
              type="password"
              placeholder="비밀번호"
              name="password"
              onChange={changeValue}
            />
          </Form.Group>

          <Button variant="primary" type="submit">
            로그인
          </Button>
          {'  '}
          <Button variant="dark" type="submit">
            취소
          </Button>
        </Form>
      </>
    </div>
  );
};

export default MemberLogin;
