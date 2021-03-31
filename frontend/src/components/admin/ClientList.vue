<template>
	<div id="clientlist">
		<button @click="getAllClients()">AllClients</button><br />
		<table border="1">
			<tr>
				<th>회원 번호</th>
				<th>이름</th>
				<th>닉네임</th>
				<th>PhoneNum</th>
				<th>생년월일</th>
				<th>성별</th>
				<th>신고누적횟수</th>
				<th>회원상태</th>
				<th>체크</th>
			</tr>
			<tr v-for="value in model" :key="value.client_no">
				<td>{{ value.client_no }}</td>
				<td>{{ value.client_name }}</td>
				<td>{{ value.phone_number }}</td>
				<td>{{ value.birthdate }}</td>
				<td>{{ value.gender }}</td>
				<td>{{ value.warining_number }}</td>
				<td>{{ value.client_status }}</td>
				<td>
					<div class="form-check form-check-inline">
						<label class="form-check-label">
							<input
								class="form-check-input"
								type="checkbox"
								name=""
								id=""
								value="checkedValue"
							/>
							Display value
						</label>
					</div>
				</td>
			</tr>
		</table>
	</div>
</template>

<script>
let model = {};
export default {
	name: 'ClientList',
	data: function() {
		return { model: model, column: '', result: [] };
	},
	methods: {
		getAllClients: function() {
			this.axios
				.get('http://localhost/step11_BackLogic/controller?command=clientsAll')
				.then(resData => {
					this.model = resData.data;
				})
				.catch(error => {
					console.log(error);
				});
		},
	},
};
</script>
