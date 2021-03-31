<template>
	<div class="contents">
		<div class="form-wrapper form-wrapper-smtwo">
			<div class="page-header">Welcome to EduCare System!</div>
			<form @submit.prevent="submitForm" class="form">
				<div class="idandpw">
					<label class="labelone" for="id">ID : </label>
					<input id="id" type="text" v-model="id" />
					<p class="validation-text">
						<span class="warning" v-if="!isIdValid && id">
							Please enter an email address
						</span>
					</p>
				</div>
				<div>
					<label class="labelone" for="pw">PASSWORD : </label>
					<input id="pw" type="password" v-model="pw" />
				</div>
				<button
					class="btn btn-primary btn-sm"
					v-bind:disabled="!isIdValid || !pw"
					type="submit"
					id="btn"
				>
					로그인
				</button>
			</form>
		</div>
	</div>
</template>

<script>
import { validateEmail } from '@/utils/validation';
import { signinUser } from '@/api/auth';

export default {
	data() {
		return {
			//form values
			id: '',
			pw: '',
		};
	},
	computed: {
		isIdValid() {
			return validateEmail(this.id); //id가 이메일 형식이 맞는지 체크
		},
	},
	methods: {
		async submitForm() {
			try {
				// 비즈니스 로직
				const userData = {
					id: this.id,
					pw: this.pw,
				};
				const data = await signinUser(userData);

				if (data.data.desturl == 'admin') {
					alert('관리자 admin 환영합니다.');
				} else if (data.data.desturl == 'waittingroom') {
					alert('환영합니다!');
				} else {
					alert('잘못된 정보를 입력하셨거나 이미 로그인중입니다.');
				}

				this.$store.commit('setToken', data.data.token);
				this.$store.commit('setId', data.data.memberinfo.id);

				this.$session.set('userinfo', data.data.memberinfo); //브라우저 localstorage에 멤버정보 저장
				this.$router.push('../' + data.data.desturl);
			} catch (error) {
				// 에러 핸들링할 코드
				console.log(error.response.data);
				this.$router.push('../Signin');
				alert('로그인 중 문제가 발생했습니다.');
			} finally {
				this.initForm();
			}
		},
		initForm() {
			this.username = '';
			this.password = '';
		},
	},
};
</script>

<style>
.btn {
	color: white;
}
</style>
