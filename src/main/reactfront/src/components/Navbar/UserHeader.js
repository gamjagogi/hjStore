import React, {useState, useEffect } from 'react';
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import {useNavigate} from 'react-router-dom';
import { useLocation } from 'react-router-dom';




const UserHeader = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState('');
    const navigate = useNavigate();
    const location = useLocation();
    const data = location.state;



    // 로그인 상태 변경 함수
    const handleLogin = () => {
        navigate('/login');
    };

    // 로그아웃 상태 변경 함수
    const handleLogout = () => {
        setIsLoggedIn(false);
        setUserName('');
    };

    if(data) {
        setUserName(data);
    }

    return (<header>
        {isLoggedIn ? (<Card style={{width: '300px', height: '100px'}}>
            <Card.Header style={{fontSize: '16px', padding: '2px'}}>회원</Card.Header>
            <Card.Body style={{fontSize: '14px', padding: '2px'}}>
                <Card.Title style={{fontSize: '18px', marginBottom: '8px'}}>안녕하세요, {userName}님 </Card.Title>
                <Button variant="primary" size="sm" onClick={handleLogout}> 로그아웃 </Button>
                <Button variant="primary" size="sm" style={{marginLeft: '30px'}}> 회원 정보 수정 </Button>
            </Card.Body>
        </Card>) : (<Card>
            <Button onClick={handleLogin}>로그인</Button>
        </Card>)}
    </header>);
};

export default UserHeader;
