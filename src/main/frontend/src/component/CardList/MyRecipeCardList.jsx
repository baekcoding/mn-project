import React, { useState, useEffect} from 'react';
import './CardList.css';
import Card from './Card';
import axios from 'axios';
import Pagination from '../../lib/Pagination.jsx';
import { Link } from 'react-router-dom';

const MyRecipeCardList = () => {
    const [cards, setCards] = useState([]);
    //초기값을 빈 배열로 설정

    const [currentPage, setCurrentPage] = useState(0);
    // 페이징 처리에 관련한 로직 및 상태 추가

    const [totalRecipeCount, setTotalRecipeCount] = useState(0);
    //전체 글의 개수를 표시

    const cardsPerPage = 6;
    //한 페이지에 표시할 카드의 수를 정의
    // 추가

    // 현재 페이지의 URL을 가져옵니다.
    const currentURL = window.location.href;

    // URL에서 쿼리 문자열을 추출합니다.
    const queryString = currentURL.split('?')[1]; // ? 뒤의 쿼리 문자열을 추출합니다.

    // 쿼리 문자열을 파싱하여 객체로 변환합니다.
    const queryParams = {};
    if (queryString) {
        const queryParts = queryString.split('&');
        for (const part of queryParts) {
            const [key, value] = part.split('=');
            queryParams[key] = decodeURIComponent(value);
        }
    }

    // rcpNum 값을 추출합니다.
    const userNum = queryParams.userNum;

    useEffect(() => {
        axios.get("http://localhost:9999/recipe/myList?userNum=" + userNum)
            .then(response => {
                setCards(response.data);
                console.log(response.data)
                setTotalRecipeCount(response.data.length); // 레시피 개수 설정
            })
            .catch(error => {
                console.error('레시피 카드 리스트 Error fetching data:', error);
            });
    }, [userNum])
    //axios로 json데이터 가져오기


    //현재 페이지에 표시 되어야 할 카드의 시작 위치 계산
    //현재 페이지 * 한페이지에 표시할 카드 수 =  시작위치
    const offset = currentPage * cardsPerPage;

    //현재 페이지에 표시되어야 할 카드들의 배열 구성
    //cards 배열에서 offset ~ offeset+cardsperPages범위를 슬라이스해서 현재 페이지에 가져온다.
    const currentCards = cards.slice(offset, offset + cardsPerPage);

    const filteredCards = currentCards.filter((card) => card.userNum === userNum);


    //페이지 변경을 처리하며, 현재 페이지에 맞게 표시할 카드들을 슬라이스하여 렌더링하는 함수
    const handlePageChange = (selectedPage) => {
        console.log("Selected page:", selectedPage); // 현재 페이지 로깅
        setCurrentPage(selectedPage);
    };

    return (
        <div className='board-card-list container'>
            <div className='board-list-top'>
                <p className='list-total-count'>전체 {totalRecipeCount} 개 </p>
                <Link to="/recipeWrite" className='write-go'>글쓰기</Link>
            </div>
            <div className="card-list">
                {Array.isArray(filteredCards) && filteredCards.map((card, index) => (
                    <Card key={index} card={card} showTitle={true}/>
                ))}
            </div>
            <Pagination pageCount={Math.ceil(filteredCards.length / cardsPerPage)} onPageChange={(data) => handlePageChange(data.selected)} />
        </div>
    );
};

export default MyRecipeCardList;