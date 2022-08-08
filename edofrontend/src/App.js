import React, { useEffect } from 'react';
import { Container } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { Route, Routes } from 'react-router';
import Footer from './components/Footer';
import Header from './components/Header';
import MemberJoin from './page/member/MemberJoin';
import MemberLogin from './page/member/MemberLogin';
import { logout } from './store';
import CommunityMain from './page/community/CommunityMain';
import MemberLogout from './page/member/MemberLogout';

function App() {
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
          {/* 로그아웃 */}
          <Route path="/memberLogout" excat={true} element={<MemberLogout/>} />
          {/* 커뮤니티 */}
          <Route path="/communityMain" excat={true} element={<CommunityMain />} />
        </Routes>
      </Container>
      <Footer />
    </div>
  );
}

export default App;
