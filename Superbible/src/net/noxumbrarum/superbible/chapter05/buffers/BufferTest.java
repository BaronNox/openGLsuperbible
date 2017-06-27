package net.noxumbrarum.superbible.chapter05.buffers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL44;
import org.lwjgl.opengl.GL45;

import net.noxumbrarum.util.Utils;
import net.noxumbrarum.util.VectorSize;
import net.noxumbrarum.util.bindings.AttribIndex;
import net.noxumbrarum.util.bindings.BindingIndex;
import net.noxumbrarum.util.bindings.Indeces;

public class BufferTest {
	private float[] vertices = 
		{ 
			0, 1.0f, 0.5f, 1,
			-1f, -1f, 0.5f, 1,
			1f, -1f, 0.5f, 1
		};
	
	private float[] color = 
		{
			0, 1f, 0f, 1f
		};
	
	private int vao;
	private int vertexBuffer;
	
	private int shaderProgram;
	
	
	public BufferTest() {
		init();
	}

	private final void init() {
		//create shaders
		createShaders();
		createBuffer();
		
	}
	
	public void render() {
		GL30.glBindVertexArray(vao);
		GL20.glUseProgram(shaderProgram);
		//C:05 - Uniforms - Start
		GL20.glUniform4fv(AttribIndex.get(Indeces.ATTRIB_TEST), color);
		//C:05 - Uniforms - End
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
	}
	
	public void dispose() {
		GL30.glDeleteVertexArrays(vao);
		GL20.glDeleteProgram(shaderProgram);
	}

	private void createBuffer() {
		vao = GL45.glCreateVertexArrays();
		vertexBuffer = GL45.glCreateBuffers();
		GL45.glNamedBufferStorage(vertexBuffer, 3 * VectorSize.Vec4f.getSizeAsBytes(), GL44.GL_DYNAMIC_STORAGE_BIT | GL30.GL_MAP_READ_BIT);
		GL45.glNamedBufferSubData(vertexBuffer, 0, vertices);
		
		GL45.glVertexArrayVertexBuffer(vao, BindingIndex.get(Indeces.VERTIX), vertexBuffer, 0, VectorSize.Vec4f.getSizeAsBytes());
		GL45.glVertexArrayAttribBinding(vao, AttribIndex.get(Indeces.VERTIX), BindingIndex.get(Indeces.VERTIX));
		GL45.glVertexArrayAttribFormat(vao, AttribIndex.get(Indeces.VERTIX), 4, GL11.GL_FLOAT, false,  0);
		GL45.glEnableVertexArrayAttrib(vao, AttribIndex.get(Indeces.VERTIX));
	}

	private void createShaders() {
		int vertexShader;
		int fragmentShader;
		//Create shaders
		shaderProgram = GL20.glCreateProgram();
		vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		
		//Feed src to shader and compile
		GL20.glShaderSource(vertexShader, Utils.loadFileAsString("shader.vert"));
		GL20.glCompileShader(vertexShader);
		
		GL20.glShaderSource(fragmentShader, Utils.loadFileAsString("shader.frag"));
		GL20.glCompileShader(fragmentShader);
		
		//Attach shaders (duh...) and link them together
		GL20.glAttachShader(shaderProgram, vertexShader);
		GL20.glAttachShader(shaderProgram, fragmentShader);
		
		GL20.glLinkProgram(shaderProgram);
		
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
	}
	
	
}
