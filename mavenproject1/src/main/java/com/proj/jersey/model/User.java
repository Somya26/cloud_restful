/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proj.jersey.model;

/**
 *
 * @author somyagoel
 */

public final class User
{
	private final long id;
	private final String email;
	private final String password;
        public static final long EMPTY_ID = -1;
	public static final double EMPTY_SUM = -1;
	
	public User(final String email, final String password)
	{
		this(EMPTY_ID, email, password);
	}
	
	public User(final long id, final String email, final String password)
	{
		this.id = id;
		this.email = email.toLowerCase();
		this.password = password;
	}
	
	public long getId()
	{
		return id;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof User)
		{
			User otherUser = (User) other;
			return email.equals(otherUser.getEmail()) && password.equals(otherUser.getPassword());
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		int result = 1;
		result *= 31 + id;
		result *= 31 + email.hashCode();
		
		return result;
	}
}
