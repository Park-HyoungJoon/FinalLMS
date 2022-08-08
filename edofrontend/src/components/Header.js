import React, { useEffect, useState } from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { Alert,Dropdown } from 'react-bootstrap';
import DropdownMenu from 'react-bootstrap/esm/DropdownMenu';
const Header = () => {
  const [user,setUser] = useState({
    email:''
  });
  const token = useSelector((store) => store.accessToken);
  useEffect(() => {
    fetch('http://localhost:80/me').then(
    ).then(
      res => {
        console.log(res);
        setUser(res);
      }
    );
  },[])
  return (
    <>
      <Navbar bg="dark" variant="dark">
        <Container>
          <Link to="/" className="navbar-brand">
            홈
          </Link>

          <Nav className="me-auto">
            {token ===''? 
            <Dropdown>
              <Dropdown.Toggle variant='dark'>
                =
              </Dropdown.Toggle>
            <Dropdown.Menu variant='dark'>
              <Dropdown.Item><Link to="/memberLogin" className="navbar-brand">
              로그인
            </Link></Dropdown.Item>
              <Dropdown.Item><Link to="/memberJoin" className="navbar-brand">
              회원가입
            </Link></Dropdown.Item>
            </Dropdown.Menu>
            </Dropdown>
             :  
            <Link to="/memberLogout" className="navbar-brand">
              로그아웃
            </Link>}
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
