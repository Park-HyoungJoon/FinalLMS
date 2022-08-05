import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';

const MemberJoin = props => {
  const [users, setUsers] = useState({
    Email: '',
    Name: '',
    password: '',
    address: '',
  });

  const changeValue = e => {
    setUsers({
      ...users,
      [e.target.name]: e.target.value,
    });
  };

  //   회원가입 버튼 누르면 비동기 연결
  const User = e => {
    e.preventDefault();
    fetch('http://localhost:80/user', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json; charset=utf-8' },
      body: JSON.stringify(users),
    })
      .then(res => {
        console.log(1, res);
        //  잘 된 경우
        if (res.status === 200) {
          return res.json();
        }
      })
      .then(res => {
        console.log('정상', res);
        if (res !== null) {
        } else {
          alert('회원가입에 실패했습니다.');
        }
      });
  };
  return (
    <div>
      {' '}
      {/* button누르면 user 실행 */}
      <Form onSubmit={User}>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Name</Form.Label>
          <Form.Control
            type="Name"
            placeholder="이름"
            name="name"
            onChange={changeValue}
          />
        </Form.Group>
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
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>address</Form.Label>
          <Form.Control
            type="address"
            placeholder="주소"
            name="address"
            onChange={changeValue}
          />
        </Form.Group>

        {/* 여기 onclick으로 memberJoinButton 메소드 실행 예정 */}
        <Button variant="primary" type="submit">
          회원가입
        </Button>
      </Form>
    </div>
  );
};

export default MemberJoin;
