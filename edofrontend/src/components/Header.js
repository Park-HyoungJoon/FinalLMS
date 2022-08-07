import React from 'react';

import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';
const Header = () => {
  const logState = useSelector((store) => store.logState);
  return (
    <>
      <Navbar bg="dark" variant="dark">
        <Container>
          <Link to="/" className="navbar-brand">
            홈
          </Link>

          <Nav className="me-auto">
            <Link to="/memberJoin" className="navbar-brand">
              회원가입
            </Link>

            <Link to="/memberLogin" className="navbar-brand">
              로그인
            </Link>
            
            <Link to="/communityMain" className="navbar-brand">
              게시판
            </Link>
          </Nav>
        </Container>
      </Navbar>
    </>
  );
};

export default Header;
