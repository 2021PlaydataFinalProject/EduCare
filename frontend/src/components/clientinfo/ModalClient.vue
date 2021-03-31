/* eslint-disable prettier/prettier */
<template>
	<div id="modal-client">
		<!-- Button trigger modal -->
		<button
			type="button"
			class="btn btn-primary btn-lg"
			data-toggle="modal"
			data-target="#modelId"
		>
			내정보보기
		</button>

		<!-- Modal -->
		<div
			class="modal fade"
			id="modelId"
			tabindex="-1"
			role="dialog"
			aria-labelledby="modelTitleId"
			aria-hidden="true"
		>
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">회원정보</h5>
						<button
							type="button"
							class="close"
							data-dismiss="modal"
							aria-label="Close"
						>
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<InfoClient />
					</div>
					<div class="modal-footer">
						<button
							type="button"
							class="btn btn-secondary"
							data-dismiss="modal"
						>
							Close
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import InfoClient from '@/components/clientinfo/InfoClient.vue';

export default {
	name: 'ModalClient',
	components: {
		InfoClient,
	},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
	margin: 40px 0 0;
}
ul {
	list-style-type: none;
	padding: 0;
}
li {
	display: inline-block;
	margin: 0 10px;
}
a {
	color: #42b983;
}
</style>
