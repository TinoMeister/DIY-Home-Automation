namespace DIYHomeAutomationAPI.Models
{
    /// <summary>
    /// This class represents a User
    /// </summary>
    public class User
    {
        /// <summary>
        /// User's Id
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// User's Name
        /// </summary>
        public string Name { get; set; } = null!;   

        /// <summary>
        /// User's Email
        /// </summary>
        public string Email { get; set; } = null!;

        /// <summary>
        /// User's Password
        /// </summary>
        public string Password { get; set; } = null!;
    }
}
