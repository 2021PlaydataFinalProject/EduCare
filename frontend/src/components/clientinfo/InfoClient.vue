<template>
	<div>
		<table border="1" class="tbmodal">
			<tr>
				<th>회원 아이디</th>
				<th>이름</th>
				<th>닉네임</th>
				<th>회원상태</th>
			</tr>
			<tr>
				<td id="col1">{{ data.id }}</td>
				<td id="col2">{{ data.name }}</td>
				<td id="col3">{{ data.nickname }}</td>
				<td id="col4">{{ data.classification }}</td>
			</tr>
		</table>
	</div>
</template>

<script>
export default {
	data() {
		return {
			data: [],
		};
	},
	name: 'InfoClient',
	methods: {
		clientSession() {
			this.data = this.$session.get('userinfo');
		},
	},
	// 화면 시작 시 clientSession 함수 실행
	beforeMount() {
		this.clientSession();
	},
};
</script>
