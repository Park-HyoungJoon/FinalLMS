import { Container } from 'react-bootstrap';
import { Route, Routes } from 'react-router';
import Footer from './components/Footer';
import Header from './components/Header';
import MemberJoin from './page/member/MemberJoin';
import MemberLogin from './page/member/MemberLogin';

function App() {
  return (
    <div>
      <Header />
      <Container>
        <Routes>
          <Route path="/" excat={true} />
          {/* 회원가입 */}
          <Route path="/memberJoin" excat={true} element={<MemberJoin />} />
          {/* 로그인 */}
          <Route path="/memberLogin" excat={true} element={<MemberLogin />} />
          <Route path="/join" excat={true} />
          <Route path="/updateForm" excat={true} />
        </Routes>
      </Container>
      <Footer />
    </div>
  );
}

export default App;
