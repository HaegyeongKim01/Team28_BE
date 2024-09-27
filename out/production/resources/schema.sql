-- 회원(MEMBERS) 스키마
CREATE TABLE IF NOT EXISTS chat_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    company VARCHAR(255),
    position VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    timestamp TIMESTAMP
);

-- 채팅방(CHAT ROOM) 스키마
CREATE TABLE IF NOT EXISTS chat_room (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP,
    last_message VARCHAR(255),
    last_message_time TIMESTAMP
);

-- 채팅 메시지(CHAT MESSAGE) 스키마
CREATE TABLE IF NOT EXISTS chat_message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content VARCHAR(255),
    sender VARCHAR(255),
    timestamp TIMESTAMP,
    chat_room_id BIGINT,
    CONSTRAINT fk_chat_room FOREIGN KEY (chat_room_id) REFERENCES chat_room(id)
);

-- 채팅방 참가자(CHAT ROOM PARTICIPANTS) 스키마
CREATE TABLE IF NOT EXISTS chat_room_participants (
    chat_room_id BIGINT,
    participants_id BIGINT,
    PRIMARY KEY (chat_room_id, participants_id),
    CONSTRAINT fk_participants_card FOREIGN KEY (participants_id) REFERENCES chat_user(id),
    CONSTRAINT fk_participants_chat_room FOREIGN KEY (chat_room_id) REFERENCES chat_room(id)
);
