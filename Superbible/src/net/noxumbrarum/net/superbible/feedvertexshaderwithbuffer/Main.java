package net.noxumbrarum.net.superbible.feedvertexshaderwithbuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL44;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryStack;

import net.noxumbrarum.util.Utils;
import net.noxumbrarum.util.VectorSize;
import net.noxumbrarum.util.bindings.AttribIndex;
import net.noxumbrarum.util.bindings.BindingIndex;
import net.noxumbrarum.util.bindings.Indeces;

public class Main {
	// The window handle
		private long window;

		public void run() {
			System.out.println("Hello LWJGL " + Version.getVersion() + "!");

			init();
			loop();

			// Free the window callbacks and destroy the window
			glfwFreeCallbacks(window);
			glfwDestroyWindow(window);

			// Terminate GLFW and free the error callback
			glfwTerminate();
			glfwSetErrorCallback(null).free();
		}

		private void init() {
			// Setup an error callback. The default implementation
			// will print the error message in System.err.
			GLFWErrorCallback.createPrint(System.err).set();

			// Initialize GLFW. Most GLFW functions will not work before doing this.
			if ( !glfwInit() )
				throw new IllegalStateException("Unable to initialize GLFW");

			// Configure GLFW
			glfwDefaultWindowHints(); // optional, the current window hints are already the default
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
			glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
			glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GL11.GL_TRUE);

			// Create the window
			window = glfwCreateWindow(640, 480, "Hello World!", NULL, NULL);
			if ( window == NULL )
				throw new RuntimeException("Failed to create the GLFW window");

			// Setup a key callback. It will be called every time a key is pressed, repeated or released.
			glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
				if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
					glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
			});

			// Get the thread stack and push a new frame
			try ( MemoryStack stack = stackPush() ) {
				IntBuffer pWidth = stack.mallocInt(1); // int*
				IntBuffer pHeight = stack.mallocInt(1); // int*

				// Get the window size passed to glfwCreateWindow
				glfwGetWindowSize(window, pWidth, pHeight);

				// Get the resolution of the primary monitor
				GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

				// Center the window
				glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
				);
			} // the stack frame is popped automatically

			// Make the OpenGL context current
			glfwMakeContextCurrent(window);
			// Enable v-sync
			glfwSwapInterval(1);

			// Make the window visible
			glfwShowWindow(window);
		}

		private void loop() {
			GL.createCapabilities();
			GL11.glEnable(GL43.GL_DEBUG_OUTPUT);
			GL11.glEnable(GL43.GL_DEBUG_OUTPUT_SYNCHRONOUS);
			Callback debug = GLUtil.setupDebugMessageCallback(System.err);
			
			glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			
			
			float[] vertices = 
				{ 
					0, 1.0f, 0.5f, 1,
					-1f, -1f, 0.5f, 1,
					1f, -1f, 0.5f, 1
				};
			
			float[] color = 
				{
					0, 1f, 0f, 1f
				};
			int vao;
			int vertexBuffer;
			
			int shaderProgram = GL20.glCreateProgram();
			int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
			int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
			
			
			GL20.glShaderSource(vertexShader, Utils.loadFileAsString("shader.vert"));
			GL20.glCompileShader(vertexShader);
			
			GL20.glShaderSource(fragmentShader, Utils.loadFileAsString("shader.frag"));
			GL20.glCompileShader(fragmentShader);
			
			
			GL20.glAttachShader(shaderProgram, vertexShader);
			GL20.glAttachShader(shaderProgram, fragmentShader);
			
			GL20.glLinkProgram(shaderProgram);
			
			GL20.glDeleteShader(vertexShader);
			GL20.glDeleteShader(fragmentShader);
			
			//C:05 - Buffers - Start
			vao = GL45.glCreateVertexArrays();
			vertexBuffer = GL45.glCreateBuffers();
			GL45.glNamedBufferStorage(vertexBuffer, 3 * VectorSize.Vec4f.getSizeAsBytes(), GL44.GL_DYNAMIC_STORAGE_BIT | GL30.GL_MAP_READ_BIT);
			GL45.glNamedBufferSubData(vertexBuffer, 0, vertices);
			
			GL45.glVertexArrayVertexBuffer(vao, BindingIndex.get(Indeces.VERTIX), vertexBuffer, 0, VectorSize.Vec4f.getSizeAsBytes());
			GL45.glVertexArrayAttribBinding(vao, AttribIndex.get(Indeces.VERTIX), BindingIndex.get(Indeces.VERTIX));
			GL45.glVertexArrayAttribFormat(vao, AttribIndex.get(Indeces.VERTIX), 4, GL11.GL_FLOAT, false,  0);
			GL45.glEnableVertexArrayAttrib(vao, AttribIndex.get(Indeces.VERTIX));
			//C:05 - Buffers - End
			
			
//			DEBUG: Check if buffer is actually filled.
//			ByteBuffer ptr = GL45.glMapNamedBuffer(vertexBuffer, GL15.GL_READ_ONLY);
//			for(int i = 0; i < ptr.asFloatBuffer().remaining(); i++) {
//				System.out.println(ptr.asFloatBuffer().get(i));
//			}
//			GL45.glUnmapNamedBuffer(vertexBuffer);
			GL30.glBindVertexArray(vao);
			while ( !glfwWindowShouldClose(window) ) {
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
				
				
				GL20.glUseProgram(shaderProgram);
				//C:05 - Uniforms - Start
				GL20.glUniform4fv(AttribIndex.get(Indeces.ATTRIB_TEST), color);
				//C:05 - Uniforms - End
				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
				
				glfwSwapBuffers(window); 
				glfwPollEvents();
			}
			
			GL30.glDeleteVertexArrays(vao);
			GL20.glDeleteProgram(shaderProgram);
			
			debug.free();
		}

		public static void main(String[] args) {
			new Main().run();
		}
}
