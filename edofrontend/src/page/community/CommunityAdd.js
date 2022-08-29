import React, {useState} from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import DragDrop,{files} from '../../components/DragDrop';

const CommunityAdd = () => {
  const [users, setUsers] = useState({
    title: '',
    text: '',
  });

  const changeValue = e => {
    setUsers({
      ...users,
      [e.target.name]: e.target.value,
    });
  };

    const Upload = (e) => {
        e.preventDefault();
        fetch('http://localhost:80/community/upload', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json; charset=utf-8' },
          body: JSON.stringify(files,users),
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
              alert('업로드에 실패했습니다.');
            }
          });
      };
    return (
        <div>
    <Form onSubmit={Upload}>
      <Form.Group className="mb-3" controlId="formBasicTitle">
        <Form.Label>제목</Form.Label>
        <Form.Control type="label" placeholder="글의 제목을 입력해주세요."  name="title" onChange={changeValue}/>
      </Form.Group>

      <Form.Group className="mb-2" controlId="formBasicText">
        <Form.Label>글 내용</Form.Label>
        <Form.Control type="label" placeholder="글의 내용을 입력해주세요."  name="text" onChange={changeValue}/>
      </Form.Group>
      <DragDrop />
      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
        </div>
    );
};

export default CommunityAdd;