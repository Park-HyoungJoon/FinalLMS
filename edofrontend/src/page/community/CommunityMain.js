import React, { useCallback, useEffect } from 'react';
import { Stack } from 'react-bootstrap';
import { shallowEqual, useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import Paging from '../../components/page/Paging';
import Sidebar from '../../components/Sidebar';

const CommunityMain = () => {
    return (
        <div>
            {/* <Sidebar/> */}
        <Stack direction="horizontal" gap={2} >
            <Link to="/communityAdd" className="btn btn-dark ms-auto">
              글 추가
            </Link>
        </Stack>
            <Paging/>
        </div>
    );
};

export default CommunityMain;