import React,{useState} from 'react';
import { StyledComponent } from 'styled-components';
import Pagination from 'react-js-pagination';
import '../css/PagingCss.css';


const Paging = ({page,count,setPage}) => {

    return (
        <Pagination
        activePage={page}//현재 페이지
        itemsCountPerPage={10}//한 페이지당 보여줄 리스트 아이템의 개수
        totalItemsCount={count}//총 아이템의 개수
        pageRangeDisplayed={5}//Paginator 내에서 보여줄 페이지의 범위
        prevPageText={"‹"}//이전 
        nextPageText={"›"}//다음
        onChange={setPage}//페이지가 바뀔 때 핸들링해줄 함수 
        />
    );
};

export default Paging;