package net.noxumbrarum.superbible.chapter05.uniforms;

import java.nio.ByteBuffer;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL44;
import org.lwjgl.opengl.GL45;

import net.noxumbrarum.util.Utils;
import net.noxumbrarum.util.VectorSize;
import net.noxumbrarum.util.bindings.AttribIndex;
import net.noxumbrarum.util.bindings.BindingIndex;
import net.noxumbrarum.util.bindings.Indeces;

public class UBO140Test {
	private int ubo;
	private int vao;
	
	private int shaderProgram;
	
	
	
	private float[] vertices = 
		{ 
			0, 1.0f, 0.5f, 1,
			-1f, -1f, 0.5f, 1,
			1f, -1f, 0.5f, 1
		};
	
	private Vector4f color = new Vector4f().add(0, 1, 0, 0);
	private float test = 1.0f;
	
	
	public UBO140Test() {
		init();
	}


	private final void init() {
		//create shaders
		createShaders();
		createBuffer();
	}
	
	private void createBuffer() {
		vao = GL45.glCreateVertexArrays();
		int vertexBuffer = GL45.glCreateBuffers();
		GL45.glNamedBufferStorage(vertexBuffer, 3 * VectorSize.Vec4f.getSizeAsBytes(), GL44.GL_DYNAMIC_STORAGE_BIT | GL30.GL_MAP_READ_BIT);
		GL45.glNamedBufferSubData(vertexBuffer, 0, vertices);
		
		GL45.glVertexArrayVertexBuffer(vao, BindingIndex.get(Indeces.VERTIX), vertexBuffer, 0, VectorSize.Vec4f.getSizeAsBytes());
		GL45.glVertexArrayAttribBinding(vao, AttribIndex.get(Indeces.VERTIX), BindingIndex.get(Indeces.VERTIX));
		GL45.glVertexArrayAttribFormat(vao, AttribIndex.get(Indeces.VERTIX), 4, GL11.GL_FLOAT, false,  0);
		GL45.glEnableVertexArrayAttrib(vao, AttribIndex.get(Indeces.VERTIX));
		
		ubo = GL45.glCreateBuffers();
		GL45.glNamedBufferStorage(ubo, 16, GL44.GL_DYNAMIC_STORAGE_BIT | GL30.GL_MAP_WRITE_BIT | GL30.GL_MAP_READ_BIT);
		ByteBuffer ptr = GL45.glMapNamedBuffer(ubo, GL15.GL_READ_WRITE);
//		ptr.position(0);
		ptr.putFloat(color.x);
		ptr.putFloat(color.y);
		ptr.putFloat(color.z);
		ptr.putFloat(color.w);
		GL45.glUnmapNamedBuffer(ubo);
		
		GL30.glBindBufferBase(GL31.GL_UNIFORM_BUFFER, 0, ubo);
	}
	
	public void render() {
		GL30.glBindVertexArray(vao);
		GL20.glUseProgram(shaderProgram);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
	}


	private void createShaders() {
		int vertexShader;
		int fragmentShader;
		//Create shaders
		shaderProgram = GL20.glCreateProgram();
		vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		
		//Feed src to shader and compile
		GL20.glShaderSource(vertexShader, Utils.loadFileAsString("UBO1.vert"));
//		GL20.glShaderSource(vertexShader, Utils.loadFileAsString("shader.vert"));
		GL20.glCompileShader(vertexShader);
		
		GL20.glShaderSource(fragmentShader, Utils.loadFileAsString("UBO1.frag"));
//		GL20.glShaderSource(fragmentShader, Utils.loadFileAsString("shader.frag"));
		GL20.glCompileShader(fragmentShader);
		
		//Attach shaders (duh...) and link them together
		GL20.glAttachShader(shaderProgram, vertexShader);
		GL20.glAttachShader(shaderProgram, fragmentShader);
		
		GL20.glLinkProgram(shaderProgram);
		
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
	}


	public void dispose() {
		GL30.glDeleteVertexArrays(vao);
		GL20.glDeleteProgram(shaderProgram);
	}
}
