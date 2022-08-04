import React, { useState } from 'react';
import { Button, Form } from 'react-bootstrap';

const MemberLogin = (props) => {
  // 로그인을 위한 useState
  const [user, setUser] = useState({
    Email: '',
    password: '',
  });



  return (
    <div>
      <>
        <Form>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="Email"
              placeholder="이메일주소"
              name="이메일주소"
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>password</Form.Label>
            <Form.Control
              type="password"
              placeholder="비밀번호"
              name="비밀번호"
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
