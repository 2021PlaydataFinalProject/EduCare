<template>
	<div>
		<AppHeader></AppHeader>
		<router-view></router-view>
		<AppFooter></AppFooter>
	</div>
</template>

<script>
import AppHeader from '@/components/common/AppHeader.vue';
import AppFooter from '@/components/common/AppFooter.vue';

export default {
	components: {
		AppHeader,
		AppFooter,
	},
};
</script>

<style>
@import './css/reset.css';
@import './css/common.css';
</style>
