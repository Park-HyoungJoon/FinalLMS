import React, { useEffect } from 'react';
import { Container } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { Route, Routes } from 'react-router';
import Footer from './components/Footer';
import Header from './components/Header';
import MemberJoin from './page/member/MemberJoin';
import MemberLogin from './page/member/MemberLogin';
import { logout } from './store';

function App() {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(logout());
  }, []);
  return (
    <div>
      <Header />
      <Container>
        <Routes>
          <Route path="/" excat={true} />
          {/* 회원가입dsds */}
          <Route path="/memberJoin" excat={true} element={<MemberJoin />} />
          {/* 로그인 */}
          <Route path="/memberLogin" excat={true} element={<MemberLogin />} />
        </Routes>
      </Container>
      <Footer />
    </div>
  );
}

export default App;
