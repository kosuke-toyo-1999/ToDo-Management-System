package com.dmm.task.service;

import java.util.Collection;

import org.springframework.scheduling.config.Task;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.dmm.task.entity.Tasks;

public class AccountTaskDetails implements UserDetails{
	
	private Tasks task;

	public AccountTaskDetails(Tasks task) {
		this.task = task;
	}

	// ユーザに与えられている権限リストを返却する
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ROLE_" + task.getName());
	}

	// パスワードを返却する
	@Override
	public String getPassword() {
		return task.getPassword();
	}

	// ユーザー名を返却する
	@Override
	public String getUsername() {
		return task.getName();
	}

	// アカウントの有効期限の状態を判定する
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// アカウントのロック状態を判定する
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 資格情報の有効期限の状態を判定する
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 有効なユーザかを判定する
	@Override
	public boolean isEnabled() {
		return true;
	}

	// Entityを返す
	public Task getUser() {
		return getUser();
	}

	// 名前を返す
	public String getName() {
		return task.getName();
	}

}
