import React, {ReactNode, useState} from 'react';

type ModalProps = {
    isOpen: boolean;
    onClose: () => void;
    children: ReactNode;
};

const Modal = ({isOpen, onClose, children}: ModalProps) => {
    if (!isOpen) return null;

    return (
        <div
            style={{
                position: 'fixed',
                top: 0,
                left: 0,
                width: '100%',
                height: '100%',
                backgroundColor: 'rgba(0, 0, 0, 0.5)',
                display: 'flex',
                marginTop:'200px',
                justifyContent: 'center',
                alignItems: 'center',
                zIndex: 9999
            }}
            onClick={onClose}
        >
            <div
                style={{
                    backgroundColor: 'whitesmoke',
                    padding: '20px',
                    borderRadius: '8px',
                    minWidth: '300px'
                }}
                onClick={(e) => e.stopPropagation()}
            >
                {children}
            </div>
        </div>
    );
};

export default function ModalExample() {
    const [isOpen, setIsOpen] = useState(false);

    return (
        <div
            style={{
                height: '100vh',           // 화면 높이 100%
                display: 'flex',           // flexbox 사용
                justifyContent: 'center',  // 가로 중앙 정렬
                alignItems: 'center',      // 세로 중앙 정렬
                flexDirection: 'column'    // 세로 방향 정렬
            }}
        >
            <button onClick={() => {
                console.log("모달 열림");
                setIsOpen(true);
            }}
                    style={{
                        border: '1px solid #ccc',
                        padding: '10px 14px',
                        borderRadius: '4px',
                        width: '200px',
                        backgroundColor: '#fff',
                        cursor: 'pointer',
                        fontSize: '16px',
                        color: '#333',
                        boxShadow: 'inset 0 1px 3px rgba(0,0,0,0.1)',
                        userSelect: 'none'
                    }}
            >
                모달 열기
            </button>

            <Modal isOpen={isOpen} onClose={() => setIsOpen(false)}>
                <div
                    style={{
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',   // 수직 정렬 중앙
                        textAlign: 'center',    // 텍스트 중앙
                        gap: '16px'
                    }}
                    >
                <h3 style={{ marginBottom: '24px' }}>모달 제목</h3>
                <p style={{ marginBottom: '30px' }}>모달 설명</p>
                <button onClick={() => setIsOpen(false)}>
                    닫기
                </button>
                </div>
            </Modal>
        </div>
    );
}
