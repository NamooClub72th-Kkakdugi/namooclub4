
-- 사용자
DROP TABLE IF EXISTS USER RESTRICT;

-- 커뮤니티
DROP TABLE IF EXISTS Community RESTRICT;

-- 클럽
DROP TABLE IF EXISTS Club RESTRICT;

-- 커뮤니티회원
DROP TABLE IF EXISTS CommunityMember RESTRICT;

-- 클럽 회원
DROP TABLE IF EXISTS ClubMember RESTRICT;

-- 클럽 카테고리
DROP TABLE IF EXISTS ClubCategory RESTRICT;

-- 사용자
CREATE TABLE USER (
	EMAIL    VARCHAR(40) PRIMARY KEY, -- 이메일
	PASSWORD VARCHAR(20) NOT NULL, -- 비밀번호
	NAME     VARCHAR(50) NOT NULL -- 이름
);


-- 커뮤니티
CREATE TABLE Community (
	COM_NO   INTEGER      PRIMARY KEY AUTO_INCREMENT, -- 커뮤니티NO
	COM_NM   VARCHAR(255) NOT NULL  , -- 커뮤니티 이름
	COM_DES  VARCHAR(255) NOT NULL  , -- 커뮤니티 설명
	COM_DATE TIMESTAMP    NOT NULL   -- 개설 일자
);


-- 클럽
CREATE TABLE Club (
	CLUB_NO     INTEGER      PRIMARY KEY AUTO_INCREMENT , -- 클럽NO
	COM_NO      INTEGER      NOT NULL , -- 커뮤니티NO
	CATEGORY_NO INTEGER      NOT NULL , -- 카테고리 번호
	CLUB_NM     VARCHAR(255) NOT NULL , -- 클럽 이름
	CLUB_DES    VARCHAR(255) NOT NULL , -- 클럽 설명
	CLUB_DATE   TIMESTAMP    NOT NULL  -- 개설 일자
);

-- 커뮤니티회원
CREATE TABLE CommunityMember (
	EMAIL      VARCHAR(40) NOT NULL , -- 이메일
	COM_NO     INTEGER     NOT NULL , -- 커뮤니티NO
	IS_MANAGER CHAR(1)     NULL      -- 멤버구분
);

-- 커뮤니티회원
ALTER TABLE CommunityMember
	ADD CONSTRAINT PK_CommunityMember -- 커뮤니티회원 기본키
		PRIMARY KEY (
			EMAIL,  -- 이메일
			COM_NO  -- 커뮤니티NO
		);

-- 클럽 회원
CREATE TABLE ClubMember (
	EMAIL   VARCHAR(40) NOT NULL , -- 이메일
	CLUB_NO INTEGER     NOT NULL , -- 클럽NO
	TYPE    CHAR(1)     NOT NULL  -- 멤버구분
);

-- 클럽 회원
ALTER TABLE ClubMember
	ADD CONSTRAINT PK_ClubMember -- 클럽 회원 기본키
		PRIMARY KEY (
			EMAIL,   -- 이메일
			CLUB_NO  -- 클럽NO
		);

-- 클럽 카테고리
CREATE TABLE ClubCategory (
	CATEGORY_NO INTEGER     NOT NULL , -- 카테고리 번호
	COM_NO      INTEGER     NOT NULL , -- 커뮤니티NO
	CATEGORY_NM VARCHAR(50) NOT NULL  -- 카테고리 이름
);
