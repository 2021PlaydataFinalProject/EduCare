<template>
	<div class="contents">
		<div class="form-wrapper form-wrapper-sm">
			<h4>Personal registration data</h4>
			<form @submit.prevent="submitForm()" class="form">
				<div>
					<label for="memberagree">memberagree</label>
					동의하려면 체크하세요.
					<input
						v-model="memberagree"
						id="memberagree"
						type="checkbox"
						name="memberagree"
						value="memberagree"
					/>
				</div>
				<div>
					<label for="id">ID : </label>
					<input v-model="id" id="id" type="text" placeholder="xxxx@xxxx.xxx" />
				</div>
				<div>
					<label for="pw">PASSWORD : </label>
					<input v-model="pw" id="pw" type="password" />
				</div>
				<div>
					<label for="name">NAME : </label>
					<input v-model="name" id="name" type="text" />
				</div>
				<div>
					<label for="nickname">NICKNAME : </label>
					<input v-model="nickname" id="nickname" type="text" />
				</div>
				<div>
					<label for="phonenumber">PHONE NUMBER : </label>
					<input
						v-model="phonenumber"
						id="phonenumber"
						type="text"
						placeholder="01x-xxxx-xxxx"
					/>
				</div>
				<div>
					<label for="birth">BIRTH : </label>
					<input
						v-model="birth"
						id="birth"
						type="text"
						placeholder="YYYY-MM-DD"
					/>
				</div>
				<div>
					<label for="gender">남성</label>
					<input
						v-model="gender"
						id="male"
						type="radio"
						name="gender"
						value="male"
					/>
				</div>
				<div>
					<label for="gender">여성</label>
					<input
						v-model="gender"
						id="female"
						type="radio"
						name="gender"
						value="female"
					/>
				</div>
				<button
					class="btn btn-primary btn-sm"
					v-bind:disabled="
						!isIdValid ||
							!pw ||
							!name ||
							!nickname ||
							!isPhoneNumberValid ||
							!isBirthValid ||
							!gender ||
							!memberagree
					"
					type="submit"
				>
					회원 가입
				</button>
			</form>
		</div>
	</div>
</template>

<script>
import { signupUser } from '@/api/auth';
import { validateEmail } from '@/utils/validation';
import { validatePhoneNumber } from '@/utils/validation';
import { validateBirth } from '@/utils/validation';

export default {
	data() {
		return {
			//form values
			id: '',
			pw: '',
			name: '',
			nickname: '',
			phonenumber: '',
			birth: '',
			gender: '',
			memberagree: false,
		};
	},
	computed: {
		isIdValid() {
			return validateEmail(this.id); //id가 이메일 형식이 맞는지 체크
		},
		isPhoneNumberValid() {
			return validatePhoneNumber(this.phonenumber); //phonenumber가 핸드폰 번호 형식이 맞는지 체크
		},
		isBirthValid() {
			return validateBirth(this.birth); //birth가 YYYY-MM-DD 형식에 맞는지 체크
		},
	},
	methods: {
		async submitForm() {
			try {
				// 비즈니스 로직
				const userData = {
					id: this.id,
					pw: this.pw,
					name: this.name,
					nickname: this.nickname,
					phonenumber: this.phonenumber,
					birth: this.birth,
					gender: this.gender,
				};
				const data = await signupUser(userData);

				if (data.data == 'Signin') {
					this.logMessage = '회원가입성공';
					alert('회원가입성공');
				} else {
					alert('회원가입실패');
				}
				this.$router.push('../' + data.data);
			} catch (error) {
				// 에러 핸들링할 코드
				alert('회원가입 중 에러가 발생했습니다.');
				console.log(error);
			} finally {
				this.initForm();
			}
		},
		initForm() {
			this.username = '';
			this.password = '';
			this.name = '';
			this.nickname = '';
			this.phonenumber = '';
			this.birth = '';
			this.gender = '';
		},
	},
};
</script>

<style></style>
